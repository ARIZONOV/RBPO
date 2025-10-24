package com.antondemin.library_api.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

public class Book {
    private Long id;

    @NotBlank
    private String title;

    @Size(min = 1, message = "Книга должна иметь хотя бы одного автора")
    private List<Long> authorIds; // связь с Author (многие-ко-многим через список id)

    private String isbn;
    private String description;

    public Book() {
    }

    public Book(Long id, String title, List<Long> authorIds, String isbn, String description) {
        this.id = id;
        this.title = title;
        this.authorIds = authorIds;
        this.isbn = isbn;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Long> getAuthorIds() {
        return authorIds;
    }

    public void setAuthorIds(List<Long> authorIds) {
        this.authorIds = authorIds;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
