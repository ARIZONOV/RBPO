package com.antondemin.library_api.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class Fine {
    private Long id;

    @NotNull
    private Long readerId; // связь с Reader

    @NotNull
    private Long bookId; // связь с Book

    @Min(0)
    private double amount;

    private String reason; // просрочка, утрата, порча и т.п.

    public Fine() {
    }

    public Fine(Long id, Long readerId, Long bookId, double amount, String reason) {
        this.id = id;
        this.readerId = readerId;
        this.bookId = bookId;
        this.amount = amount;
        this.reason = reason;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getReaderId() {
        return readerId;
    }

    public void setReaderId(Long readerId) {
        this.readerId = readerId;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
