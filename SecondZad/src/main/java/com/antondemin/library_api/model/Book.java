package com.antondemin.library_api.model;

import jakarta.validation.constraints.NotBlank;
import java.util.List;

public class Book {
    private Long id;

    @NotBlank
    private String title;

    private List<String> authors;

    // геттеры и сеттеры
}
