package com.team418.api.lending.dto;

import java.time.LocalDate;

public record CreateLendingDto(String bookIsbn, String memberInss, LocalDate dueDate) {
}