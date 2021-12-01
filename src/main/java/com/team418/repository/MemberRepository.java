package com.team418.repository;

import com.team418.domain.user.Member;
import com.team418.domain.user.User;
import com.team418.exception.EmailNotUniqueException;
import com.team418.exception.InssNotUniqueException;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class MemberRepository {
    private final Map<String, Member> members;

    public MemberRepository() {
        members = new ConcurrentHashMap<>();
    }

    public void addMember(Member member) {
        assertEmailIsUnique(member.getEmail());
        assertInssIsUnique(member.getInss());
        members.put(member.getUniqueId(), member);
    }

    public Map<String, Member> getMembers() {
        return members;
    }

    private void assertEmailIsUnique(String email) {
        members.values().forEach(user -> {
            if (user.getEmail().equals(email))
                throw new EmailNotUniqueException(email);
        });
    }

    private void assertInssIsUnique(String inss) {
        members.values().forEach(member -> {
            if (member.getInss().equals(inss))
                throw new InssNotUniqueException(inss);
        });
    }

    public Member getByEmail(String email) {
        return members.values().stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst().orElse(null);
    }
}
