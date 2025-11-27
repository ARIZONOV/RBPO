package com.antondemin.library_api.controller;

import com.antondemin.library_api.model.Book;
import com.antondemin.library_api.repository.BookRepository;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookRepository bookRepo;

    public BookController(BookRepository bookRepo) {
        this.bookRepo = bookRepo;
    }

    @PostMapping
    public Book create(@Valid @RequestBody Book book) {
        return bookRepo.save(book);
    }

    @GetMapping("/{id}")
    public Book get(@PathVariable Long id) {
        return bookRepo.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Book not found: " + id));
    }

    @GetMapping
    public List<Book> list() {
        return bookRepo.findAll();
    }

    @PutMapping("/{id}")
    public Book update(@PathVariable Long id, @Valid @RequestBody Book updated) {
        if (!bookRepo.existsById(id)) {
            throw new NoSuchElementException("Book not found: " + id);
        }
        updated.setId(id);
        return bookRepo.save(updated);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        bookRepo.deleteById(id);
    }
}
