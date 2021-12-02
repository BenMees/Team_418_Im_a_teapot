package com.team418.api.lending;

import com.team418.api.lending.dto.CreateLendingDto;
import com.team418.api.lending.dto.LendingDto;
import com.team418.domain.lending.Lending;

public class LendingMapper {

    public static Lending createLendingDtoToLending(CreateLendingDto createLendingDto, String inss) {
        return new Lending(createLendingDto.isbn(), inss);
    }

    public static LendingDto lendingToLendingDto(Lending lending) {
        return new LendingDto(lending.getUniqueId(), lending.getBookIsbn(), lending.getMemberId(),lending.getDueDate());
    }
}
