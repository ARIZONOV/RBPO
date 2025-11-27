package com.antondemin.library_api.controller;

import com.antondemin.library_api.model.Fine;
import com.antondemin.library_api.repository.FineRepository;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/fines")
public class FineController {

    private final FineRepository fineRepo;

    public FineController(FineRepository fineRepo) {
        this.fineRepo = fineRepo;
    }

    @PostMapping
    public Fine create(@Valid @RequestBody Fine fine) {
        return fineRepo.save(fine);
    }

    @GetMapping("/{id}")
    public Fine get(@PathVariable Long id) {
        return fineRepo.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Fine not found: " + id));
    }

    @GetMapping
    public List<Fine> list() {
        return fineRepo.findAll();
    }

    @PutMapping("/{id}")
    public Fine update(@PathVariable Long id, @Valid @RequestBody Fine updated) {
        if (!fineRepo.existsById(id)) {
            throw new NoSuchElementException("Fine not found: " + id);
        }
        updated.setId(id);
        return fineRepo.save(updated);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        fineRepo.deleteById(id);
    }
}
