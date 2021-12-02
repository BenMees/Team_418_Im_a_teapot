package com.team418.api.lending.dto;

import java.time.LocalDate;

public record LendingDto(String uniqueLendingId, String bookIsbn, String memberId,
                         LocalDate dueDate) {
}
