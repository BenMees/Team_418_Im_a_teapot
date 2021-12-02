package com.team418.repository;

import com.team418.domain.lending.Lending;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;

public class LendingRepositoryTest {
    private LendingRepository lendingRepository;
    private Lending lending1;
    private Lending lending2;
    private Map<String, Lending> lendings;

    @BeforeEach
    void setUp() {
        lendingRepository = new LendingRepository();
        lending1 = new Lending("123456","654321");
        lending2 = new Lending("78910","987654");
        lendings = new HashMap<>();
    }


    @Test
    void whenRepository_HasNoLendings_weGetEmptyMapBack(){
        Assertions.assertThat(lendingRepository.getLendings().size()).isEqualTo(0);
    }

    @Test
    void givenALendingRepository_whenAddingALending_thenLendingRepositoryContainsThatLending() {
        lendingRepository.addLending(lending1);

        Assertions.assertThat(lendingRepository.getLendings().contains(lending1)).isTrue();
    }

    @Test
    void givenALendingRepositoryWithOneLending_whenGettingOneLending_thenLendingShouldBeReturned() {
        lendingRepository.addLending(lending1);

        Lending expectedLending = lendingRepository.getLendings().get(0);

        Assertions.assertThat(expectedLending).isEqualTo(lending1);
    }

    @Test
    void whenGettingAllLendings_WeGetTheTwoLendings() {
        lendings.put(lending1.getUniqueLendingId(), lending1);
        lendings.put(lending2.getUniqueLendingId(), lending2);

        lendingRepository.addLending(lending1);
        lendingRepository.addLending(lending2);

        Assertions.assertThat(lendingRepository.getLendings()).isEqualTo(lendings.values().stream().toList());
    }

}
