package com.team418.api.user.dto;

import com.team418.domain.Address;
import org.springframework.lang.Nullable;

public record MemberDto(String id, String firstName, String lastName, String email, @Nullable String inss, Address address) {
}