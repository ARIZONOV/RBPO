package com.antondemin.library_api.controller;

import com.antondemin.library_api.model.Reader;
import com.antondemin.library_api.repository.ReaderRepository;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/readers")
public class ReaderController {

    private final ReaderRepository readerRepo;

    public ReaderController(ReaderRepository readerRepo) {
        this.readerRepo = readerRepo;
    }

    @PostMapping
    public Reader create(@Valid @RequestBody Reader reader) {
        return readerRepo.save(reader);
    }

    @GetMapping("/{id}")
    public Reader get(@PathVariable Long id) {
        return readerRepo.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Reader not found: " + id));
    }

    @GetMapping
    public List<Reader> list() {
        return readerRepo.findAll();
    }

    @PutMapping("/{id}")
    public Reader update(@PathVariable Long id, @Valid @RequestBody Reader updated) {
        if (!readerRepo.existsById(id)) {
            throw new NoSuchElementException("Reader not found: " + id);
        }
        updated.setId(id);
        return readerRepo.save(updated);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        readerRepo.deleteById(id);
    }
}
