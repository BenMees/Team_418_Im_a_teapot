package com.team418.api.user.dto;

import com.team418.domain.Address;

public record MemberDto(String id, String firstName, String lastName, String email, String inss, Address address) {
}
