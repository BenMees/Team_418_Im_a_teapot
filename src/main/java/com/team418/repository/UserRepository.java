package com.team418.repository;

import com.team418.domain.user.Admin;
import com.team418.domain.user.Librarian;
import com.team418.domain.user.User;
import com.team418.exception.EmailNotUniqueException;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class UserRepository {
    private final Map<String, User> users;

    public UserRepository() {
        users = new ConcurrentHashMap<>();
        createDefaultAdmin();
        createDefaultLibrarian();
    }

    public User getByEmail(String email){
        return users.values().stream()
                .filter(user->user.getEmail().equals(email))
                .findFirst().orElse(null);
    }

    private void createDefaultAdmin(){
        Admin admin = new Admin("admin","default","default@switchfully.com");
        users.put(admin.getUniqueId(),admin);
    }

    private void createDefaultLibrarian() {
        Librarian sophia = new Librarian("Sophia", "abc", "abc@library.com");
        users.put(sophia.getUniqueId(), sophia);
    }

    public void addUser(User user){
        assertEmailIsUnique(user.getEmail());
        users.put(user.getUniqueId(),user);
    }

    private void assertEmailIsUnique(String email){
        users.values().forEach(user -> {
            if(user.getEmail().equals(email))
                throw new EmailNotUniqueException(email+" is already used.");
        });
    }

}
