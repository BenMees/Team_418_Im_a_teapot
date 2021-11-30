package com.team418.services;


import com.team418.domain.user.Librarian;
import com.team418.domain.user.Member;
import com.team418.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void addLibrarian(Librarian librarian){
        userRepository.addUser(librarian);
    }

    public void createMember(Member member){
        userRepository.addUser(member);
    }
}
