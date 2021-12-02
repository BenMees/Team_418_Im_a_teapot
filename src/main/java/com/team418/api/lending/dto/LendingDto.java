package com.team418.api.lending.dto;

import java.time.LocalDate;

public record LendingDto(String uniqueId, String bookIsbn, String memberInss, LocalDate dueDate) {
}
