package com.team418.repository;

import com.team418.domain.lending.Lending;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class LendingRepository {

    private final Map<String, Lending> lends = new ConcurrentHashMap<>();

    public void addLending(Lending lending) {
        lends.put(lending.getUniqueId(), lending);
    }

    public List<Lending> getLendings() {
        return lends.values().stream().toList();
    }

    public Lending getLendingById(String uniqueId) {
        return lends.get(uniqueId);
    }
}
