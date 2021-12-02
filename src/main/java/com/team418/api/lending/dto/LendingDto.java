package com.team418.api.lending.dto;

import java.time.LocalDate;
import java.util.Objects;

public record LendingDto(String uniqueId, String bookIsbn, String memberId,
                         LocalDate dueDate) {

}
