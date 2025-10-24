package com.antondemin.library_api.controller;

import com.antondemin.library_api.model.Reader;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/readers")
public class ReaderController {

    private final Map<Long, Reader> readers = new HashMap<>();
    private long nextId = 1L;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Reader create(@Valid @RequestBody Reader reader) {
        reader.setId(nextId++);
        readers.put(reader.getId(), reader);
        return reader;
    }

    @GetMapping("/{id}")
    public Reader get(@PathVariable Long id) {
        Reader r = readers.get(id);
        if (r == null)
            throw new NoSuchElementException("Reader not found: " + id);
        return r;
    }

    @GetMapping
    public List<Reader> list() {
        return new ArrayList<>(readers.values());
    }

    @PutMapping("/{id}")
    public Reader update(@PathVariable Long id, @Valid @RequestBody Reader updated) {
        if (!readers.containsKey(id))
            throw new NoSuchElementException("Reader not found: " + id);
        updated.setId(id);
        readers.put(id, updated);
        return updated;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        if (readers.remove(id) == null)
            throw new NoSuchElementException("Reader not found: " + id);
    }
}
