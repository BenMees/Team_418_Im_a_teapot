package com.team418.services;

import com.team418.domain.lending.Lending;
import com.team418.repository.LendingRepository;
import org.springframework.stereotype.Service;

@Service
public class LendingService {
    private final LendingRepository lendingRepository;

    public LendingService(LendingRepository lendingRepository) {
        this.lendingRepository = lendingRepository;
    }

    public Lending addLending(Lending lending) {
        return lendingRepository.addLending(lending);
    }
}
