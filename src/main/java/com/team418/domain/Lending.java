package com.team418.domain;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

public class Lending {
    public static final int LENDING_DAYS = 21;
    private final String uniqueId;
    private final String bookIsbn;
    private final String memberInss;
    private final LocalDate dueDate;
//    private boolean isReturned = false;

    public Lending(String bookIsbn, String memberInss) {
        this.uniqueId = UUID.randomUUID().toString();
        this.bookIsbn = bookIsbn;
        this.memberInss = memberInss;
        this.dueDate = LocalDate.now().plusDays(LENDING_DAYS);
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public String getBookIsbn() {
        return bookIsbn;
    }

    public String getMemberInss() {
        return memberInss;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    @Override
    public String toString() {
        return "Lending{" +
                "uniqueId='" + uniqueId + '\'' +
                ", bookIsbn='" + bookIsbn + '\'' +
                ", memberInss='" + memberInss + '\'' +
                ", dueDate=" + dueDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Lending lending = (Lending) o;
        return Objects.equals(uniqueId, lending.uniqueId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uniqueId);
    }
}
