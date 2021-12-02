package com.team418.repository;

import com.team418.domain.user.Admin;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class AdminRepository {
    private final Map<String, Admin> admins;

    public AdminRepository() {
        admins = new ConcurrentHashMap<>();
        createDefaultAdmin();
    }

    private void createDefaultAdmin() {
        Admin admin = new Admin("admin", "default", "default@switchfully.com");
        admins.put(admin.getUniqueId(), admin);
    }

    public Admin getByEmail(String email) {
        return admins.values().stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst().orElse(null);
    }

    /**
     * This method is purely for TESTING currently,
     * and should be refactored when implementing story 8: Register Admin
     * @param admin the admin to add
     */
    public void addNewAdmin(Admin admin) {
        admins.put(admin.getUniqueId(), admin);
    }
}
