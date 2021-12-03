package com.team418.api.book.dto;

import com.team418.domain.Author;
import org.springframework.lang.Nullable;

public record UpdateBookDto(@Nullable String title, @Nullable Author author, @Nullable String summary,
                            @Nullable Boolean isDeleted) {
}
