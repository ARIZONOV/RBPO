package com.antondemin.library_api.controller;

import com.antondemin.library_api.model.Author;
import com.antondemin.library_api.repository.AuthorRepository;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/authors")
public class AuthorController {

    private final AuthorRepository authorRepo;

    public AuthorController(AuthorRepository authorRepo) {
        this.authorRepo = authorRepo;
    }

    @PostMapping
    public Author create(@Valid @RequestBody Author author) {
        return authorRepo.save(author);
    }

    @GetMapping("/{id}")
    public Author get(@PathVariable Long id) {
        return authorRepo.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Author not found: " + id));
    }

    @GetMapping
    public List<Author> list() {
        return authorRepo.findAll();
    }

    @PutMapping("/{id}")
    public Author update(@PathVariable Long id, @Valid @RequestBody Author updated) {
        if (!authorRepo.existsById(id)) {
            throw new NoSuchElementException("Author not found: " + id);
        }
        updated.setId(id);
        return authorRepo.save(updated);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        authorRepo.deleteById(id);
    }
}
