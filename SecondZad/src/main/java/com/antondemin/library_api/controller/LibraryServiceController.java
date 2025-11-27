package com.antondemin.library_api.controller;

import com.antondemin.library_api.model.Author;
import com.antondemin.library_api.model.Book;
import com.antondemin.library_api.model.Fine;
import com.antondemin.library_api.model.Reader;
import com.antondemin.library_api.repository.AuthorRepository;
import com.antondemin.library_api.repository.BookRepository;
import com.antondemin.library_api.repository.FineRepository;
import com.antondemin.library_api.repository.ReaderRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;

@RestController
@RequestMapping("/api/library")
public class LibraryServiceController {

    private final BookRepository bookRepo;
    private final ReaderRepository readerRepo;
    private final FineRepository fineRepo;
    private final AuthorRepository authorRepo;

    private final Map<Long, Set<Long>> issuedBooks = new HashMap<>();

    public LibraryServiceController(
            BookRepository bookRepo,
            ReaderRepository readerRepo,
            FineRepository fineRepo,
            AuthorRepository authorRepo) {
        this.bookRepo = bookRepo;
        this.readerRepo = readerRepo;
        this.fineRepo = fineRepo;
        this.authorRepo = authorRepo;
    }

    @PostMapping("/issue")
    @ResponseStatus(HttpStatus.OK)
    public Map<String, Object> issueBook(@RequestParam Long readerId, @RequestParam Long bookId) {
        ensureReaderExists(readerId);
        ensureBookExists(bookId);

        issuedBooks.computeIfAbsent(readerId, k -> new HashSet<>());
        Set<Long> userBooks = issuedBooks.get(readerId);

        if (userBooks.contains(bookId)) {
            return Map.of(
                    "status", "already-issued",
                    "message", "Книга уже выдана этому читателю",
                    "readerId", readerId,
                    "bookId", bookId);
        }

        userBooks.add(bookId);
        return Map.of(
                "status", "issued",
                "message", "Книга успешно выдана",
                "readerId", readerId,
                "bookId", bookId,
                "date", LocalDate.now().toString());
    }

    @PostMapping("/return")
    @ResponseStatus(HttpStatus.OK)
    public Map<String, Object> returnBook(@RequestParam Long readerId, @RequestParam Long bookId) {
        ensureReaderExists(readerId);
        ensureBookExists(bookId);

        Set<Long> userBooks = issuedBooks.getOrDefault(readerId, Collections.emptySet());
        if (!userBooks.contains(bookId)) {
            return Map.of(
                    "status", "not-found",
                    "message", "Эта книга не числится за читателем",
                    "readerId", readerId,
                    "bookId", bookId);
        }

        userBooks.remove(bookId);
        if (userBooks.isEmpty()) {
            issuedBooks.remove(readerId);
        }

        return Map.of(
                "status", "returned",
                "message", "Книга возвращена",
                "readerId", readerId,
                "bookId", bookId,
                "date", LocalDate.now().toString());
    }

    @GetMapping("/reader-books/{readerId}")
    public List<Book> getReaderBooks(@PathVariable Long readerId) {
        ensureReaderExists(readerId);
        Set<Long> userBooks = issuedBooks.getOrDefault(readerId, Collections.emptySet());
        List<Book> result = new ArrayList<>();
        for (Long bid : userBooks) {
            bookRepo.findById(bid).ifPresent(result::add);
        }
        return result;
    }

    @PostMapping("/fine")
    @ResponseStatus(HttpStatus.CREATED)
    public Fine createFine(
            @RequestParam Long readerId,
            @RequestParam Long bookId,
            @RequestParam double amount,
            @RequestParam(defaultValue = "Просрочка возврата") String reason) {
        ensureReaderExists(readerId);
        ensureBookExists(bookId);

        Fine fine = new Fine();
        fine.setReaderId(readerId);
        fine.setBookId(bookId);
        fine.setAmount(amount);
        fine.setReason(reason);

        return fineRepo.save(fine);
    }

    @GetMapping("/author-stats/{authorId}")
    public Map<String, Object> getAuthorStats(@PathVariable Long authorId) {
        ensureAuthorExists(authorId);

        List<Book> allBooks = bookRepo.findAll();
        List<Book> authorBooks = new ArrayList<>();
        for (Book b : allBooks) {
            List<Long> aids = b.getAuthorIds();
            if (aids != null && aids.contains(authorId)) {
                authorBooks.add(b);
            }
        }

        int issuedCount = 0;
        Set<Long> allIssued = new HashSet<>();
        for (Set<Long> set : issuedBooks.values()) {
            allIssued.addAll(set);
        }
        for (Book b : authorBooks) {
            if (allIssued.contains(b.getId())) {
                issuedCount++;
            }
        }

        return Map.of(
                "authorId", authorId,
                "totalBooks", authorBooks.size(),
                "issuedBooks", issuedCount);
    }

    // Проверки
    private void ensureReaderExists(Long readerId) {
        if (!readerRepo.existsById(readerId)) {
            throw new NoSuchElementException("Reader not found: " + readerId);
        }
    }

    private void ensureBookExists(Long bookId) {
        if (!bookRepo.existsById(bookId)) {
            throw new NoSuchElementException("Book not found: " + bookId);
        }
    }

    private void ensureAuthorExists(Long authorId) {
        if (!authorRepo.existsById(authorId)) {
            throw new NoSuchElementException("Author not found: " + authorId);
        }
    }
}
