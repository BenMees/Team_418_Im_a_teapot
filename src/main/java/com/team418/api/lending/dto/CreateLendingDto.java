package com.team418.api.lending.dto;

import java.util.Objects;

public final class CreateLendingDto {
    private final String isbn;

    public CreateLendingDto(String isbn) {
        this.isbn = isbn;
    }

    public String isbn() {
        return isbn;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (CreateLendingDto) obj;
        return Objects.equals(this.isbn, that.isbn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(isbn);
    }

    @Override
    public String toString() {
        return "CreateLendingDto[" +
                "isbn=" + isbn + ']';
    }

}