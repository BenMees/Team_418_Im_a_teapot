package com.team418.domain.lending;

import com.team418.exception.LendingIsAlreadyReturnedException;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

public class Lending {
    public static final int LENDING_WEEKS = 3;
    private final String uniqueLendingId;
    private final String bookIsbn;
    private final String memberId;
    private LocalDate dueDate;
    private boolean isReturned = false;

    public Lending(String bookIsbn, String memberId) {
        this.uniqueLendingId = UUID.randomUUID().toString();
        this.bookIsbn = bookIsbn;
        this.memberId = memberId;
        this.dueDate = LocalDate.now().plusWeeks(LENDING_WEEKS);
    }

    public String getUniqueLendingId() {
        return uniqueLendingId;
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

    public boolean isReturned() {
        return isReturned;
    }

    public String returnBook() {
        if (isReturned){
           throw new LendingIsAlreadyReturnedException();
        }
        isReturned = true;
        return getCorrectResponseBasedOnTiming();
    }

    private String getCorrectResponseBasedOnTiming() {
        if (LocalDate.now().isAfter(dueDate)) {
            return "The book is returned too late.";
        }
        return "Book is returned on time";
    }

    /**
     * only used for testing purposes right now
     * @param dueDate this is date the book should be returned
     * @return the lending with the new date
     */
    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    @Override
    public String toString() {
        return "Lending{" +
                "uniqueLendingId='" + uniqueLendingId + '\'' +
                ", bookIsbn='" + bookIsbn + '\'' +
                ", memberInss='" + memberId + '\'' +
                ", dueDate=" + dueDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Lending lending = (Lending) o;
        return Objects.equals(uniqueLendingId, lending.uniqueLendingId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uniqueLendingId);
    }
}
