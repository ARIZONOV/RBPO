package com.antondemin.library_api.controller;

import com.antondemin.library_api.model.Author;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/authors")
public class AuthorController {

    private final Map<Long, Author> authors = new HashMap<>();
    private long nextId = 1L;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Author create(@Valid @RequestBody Author author) {
        author.setId(nextId++);
        authors.put(author.getId(), author);
        return author;
    }

    @GetMapping("/{id}")
    public Author get(@PathVariable Long id) {
        Author a = authors.get(id);
        if (a == null) {
            throw new NoSuchElementException("Author not found: " + id);
        }
        return a;
    }

    @GetMapping
    public List<Author> list() {
        return new ArrayList<>(authors.values());
    }

    @PutMapping("/{id}")
    public Author update(@PathVariable Long id, @Valid @RequestBody Author updated) {
        if (!authors.containsKey(id)) {
            throw new NoSuchElementException("Author not found: " + id);
        }
        updated.setId(id);
        authors.put(id, updated);
        return updated;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        if (authors.remove(id) == null) {
            throw new NoSuchElementException("Author not found: " + id);
        }
    }

    public Map<Long, Author> getAuthorsMap() {
        return authors;
    }
}
