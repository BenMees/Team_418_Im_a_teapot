package com.team418.repository;

import com.team418.domain.user.Admin;
import com.team418.domain.user.User;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class UserRepository {
    private final Map<String, User> users;

    public UserRepository() {
        users = new ConcurrentHashMap<>();
        createDefaultAdmin();
    }

    public User getByEmail(String email){
        return users.values().stream()
                .filter(user->user.getEmail().equals(email))
                .findFirst().orElse(null);
    }

    public void createDefaultAdmin(){
        Admin admin = new Admin("admin","default","default@switchfully.com");
        users.put(admin.getUniqueId(),admin);
    }
}
