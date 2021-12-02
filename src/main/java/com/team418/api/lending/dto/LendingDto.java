package com.team418.api.lending.dto;

import java.time.LocalDate;
import java.util.Objects;

public final class LendingDto {
    private final String uniqueId;
    private final String bookIsbn;
    private final String memberInss;
    private final LocalDate dueDate;

    public LendingDto(String uniqueId, String bookIsbn, String memberInss, LocalDate dueDate) {
        this.uniqueId = uniqueId;
        this.bookIsbn = bookIsbn;
        this.memberInss = memberInss;
        this.dueDate = dueDate;
    }

    public String uniqueId() {
        return uniqueId;
    }

    public String bookIsbn() {
        return bookIsbn;
    }

    public String memberInss() {
        return memberInss;
    }

    public LocalDate dueDate() {
        return dueDate;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (LendingDto) obj;
        return Objects.equals(this.uniqueId, that.uniqueId) &&
                Objects.equals(this.bookIsbn, that.bookIsbn) &&
                Objects.equals(this.memberInss, that.memberInss) &&
                Objects.equals(this.dueDate, that.dueDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uniqueId, bookIsbn, memberInss, dueDate);
    }

    @Override
    public String toString() {
        return "LendingDto[" +
                "uniqueId=" + uniqueId + ", " +
                "bookIsbn=" + bookIsbn + ", " +
                "memberInss=" + memberInss + ", " +
                "dueDate=" + dueDate + ']';
    }

}
