package com.team418.repository;

import com.team418.domain.Lending;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class LendingRepository {

    private final Map<String, Lending> lends = new ConcurrentHashMap<>();

    public Lending addLending(Lending lending) {

        return lends.put(lending.getUniqueId(),lending);
    }
}
