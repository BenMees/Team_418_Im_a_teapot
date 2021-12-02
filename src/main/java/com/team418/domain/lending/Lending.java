package com.team418.domain.lending;

import com.team418.exception.LendingIsAlreadyReturnedException;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

public class Lending {
    public static final int LENDING_WEEKS = 3;
    private final String uniqueId;
    private final String bookIsbn;
    private final String memberId;
    private final LocalDate dueDate;
   private boolean isReturned = false;

    public Lending(String bookIsbn, String memberId) {
        this.uniqueId = UUID.randomUUID().toString();
        this.bookIsbn = bookIsbn;
        this.memberId = memberId;
        this.dueDate = LocalDate.now().plusWeeks(LENDING_WEEKS);
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public String getBookIsbn() {
        return bookIsbn;
    }

    public String getMemberId() {
        return memberId;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public String returnBook() {
        if (isReturned){
           throw new LendingIsAlreadyReturnedException();
        }
        isReturned = true;
        return getCorrectResponsBasedOnTiming();
    }

    private String getCorrectResponsBasedOnTiming() {
        if (LocalDate.now().isAfter(dueDate)) {
            return "The book is returned too late.";
        }
        return "Book is returned on time";
    }

    @Override
    public String toString() {
        return "Lending{" +
                "uniqueId='" + uniqueId + '\'' +
                ", bookIsbn='" + bookIsbn + '\'' +
                ", memberInss='" + memberId + '\'' +
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
