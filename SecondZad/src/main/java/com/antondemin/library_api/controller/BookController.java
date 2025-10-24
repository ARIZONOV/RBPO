package com.antondemin.library_api.controller;

import com.antondemin.library_api.model.Book;
import com.antondemin.library_api.model.Author;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final Map<Long, Book> books = new HashMap<>();
    private final Map<Long, Author> authorRef; // теперь ссылка на живую Map авторов
    private long nextId = 1L;

    public BookController(AuthorController authorController) {
        this.authorRef = authorController.getAuthorsMap();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Book create(@Valid @RequestBody Book book) {
        validateAuthorsExist(book.getAuthorIds());
        book.setId(nextId++);
        books.put(book.getId(), book);
        return book;
    }

    @GetMapping("/{id}")
    public Book get(@PathVariable Long id) {
        Book b = books.get(id);
        if (b == null) {
            throw new NoSuchElementException("Book not found: " + id);
        }
        return b;
    }

    @GetMapping
    public List<Book> list() {
        return new ArrayList<>(books.values());
    }

    @PutMapping("/{id}")
    public Book update(@PathVariable Long id, @Valid @RequestBody Book updated) {
        if (!books.containsKey(id)) {
            throw new NoSuchElementException("Book not found: " + id);
        }
        validateAuthorsExist(updated.getAuthorIds());
        updated.setId(id);
        books.put(id, updated);
        return updated;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        if (books.remove(id) == null) {
            throw new NoSuchElementException("Book not found: " + id);
        }
    }

    private void validateAuthorsExist(List<Long> authorIds) {
        if (authorIds == null || authorIds.isEmpty()) {
            throw new IllegalArgumentException("authorIds must not be empty");
        }
        for (Long aid : authorIds) {
            if (!authorRef.containsKey(aid)) {
                throw new NoSuchElementException("Author not found for id: " + aid);
            }
        }
    }
}
