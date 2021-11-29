package com.team418.repository;

import com.team418.domain.User;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class UserRepository {
    private final Map<UUID, User> users;

    public UserRepository() {
        users = new ConcurrentHashMap<>();
    }

    
}
