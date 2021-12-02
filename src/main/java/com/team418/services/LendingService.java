package com.team418.services;

import com.team418.api.lending.LendingMapper;
import com.team418.api.lending.dto.LendingDto;
import com.team418.domain.lending.Lending;
import com.team418.repository.LendingRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LendingService {
    private final LendingRepository lendingRepository;

    public LendingService(LendingRepository lendingRepository) {
        this.lendingRepository = lendingRepository;
    }

    public void addLending(Lending lending) {
        lendingRepository.addLending(lending);
    }

    public List<LendingDto> getOverdueLendings() {
        return lendingRepository.getLendings().stream()
                .filter(this::isLendingOverdue)
                .map(LendingMapper::lendingToLendingDto)
                .collect(Collectors.toList());
    }

    public boolean isLendingOverdue(Lending lending) {
        return lending.getDueDate().isBefore(LocalDate.now());
    }


}
