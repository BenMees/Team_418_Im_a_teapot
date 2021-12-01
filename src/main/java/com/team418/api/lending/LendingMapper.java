package com.team418.api.lending;

import com.team418.api.lending.dto.CreateLendingDto;
import com.team418.api.lending.dto.LendingDto;
import com.team418.domain.lending.Lending;

public class LendingMapper {

    public static Lending createLendingDtoToLending(CreateLendingDto createLendingDto) {
        return new Lending(createLendingDto.bookIsbn(), createLendingDto.memberInss());
    }

    public static LendingDto lendingToLendingDto(Lending lending) {
        return new LendingDto(lending.getUniqueId(), lending.getBookIsbn(), lending.getMemberInss(),lending.getDueDate());
    }
}
