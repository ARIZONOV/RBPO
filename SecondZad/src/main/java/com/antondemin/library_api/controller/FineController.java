package com.antondemin.library_api.controller;

import com.antondemin.library_api.model.Fine;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/fines")
public class FineController {

    private final Map<Long, Fine> fines = new HashMap<>();
    private final BookController bookController;
    private final ReaderController readerController;
    private long nextId = 1L;

    public FineController(BookController bookController, ReaderController readerController) {
        this.bookController = bookController;
        this.readerController = readerController;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Fine create(@Valid @RequestBody Fine fine) {
        // проверяем существование reader и book
        readerController.get(fine.getReaderId());
        bookController.get(fine.getBookId());
        fine.setId(nextId++);
        fines.put(fine.getId(), fine);
        return fine;
    }

    @GetMapping("/{id}")
    public Fine get(@PathVariable Long id) {
        Fine f = fines.get(id);
        if (f == null)
            throw new NoSuchElementException("Fine not found: " + id);
        return f;
    }

    @GetMapping
    public List<Fine> list() {
        return new ArrayList<>(fines.values());
    }

    @PutMapping("/{id}")
    public Fine update(@PathVariable Long id, @Valid @RequestBody Fine updated) {
        if (!fines.containsKey(id))
            throw new NoSuchElementException("Fine not found: " + id);
        // проверим связи при обновлении
        readerController.get(updated.getReaderId());
        bookController.get(updated.getBookId());
        updated.setId(id);
        fines.put(id, updated);
        return updated;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        if (fines.remove(id) == null)
            throw new NoSuchElementException("Fine not found: " + id);
    }
}
