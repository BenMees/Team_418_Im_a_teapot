package com.team418.repository;


import com.team418.domain.user.Member;
import com.team418.exception.EmailAddressIsInvalidException;
import com.team418.exception.EmailNotUniqueException;
import com.team418.exception.InssNotUniqueException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.Assert.fail;

public class MemberRepositoryTest {
    private HashMap<String, Member> members;
    private MemberRepository repository;
    private Member member1;
    private Member member2;

    @BeforeEach
    void setup() {
        members = new HashMap<>();
        member1 = new Member("Holy", "Banjo", "chickchack@hotmail.com", "1345");
        member2 = new Member("yloH", "ojnaB", "chackchick@hotmail.com", "1323453445");

        members.put(member1.getUniqueId(),member1);
        members.put(member2.getUniqueId(),member2);

        repository = new MemberRepository();
    }

    @Test
    void whenRepository_HasNoMembers_weGetEmptyMapBack() {
        Assertions.assertThat(repository.getMembers().size()).isEqualTo(0);
    }

    @Test
    void addingNewMember_toTheMemberRepository(){
        repository.addMember(member1);
        repository.addMember(member2);

        Assertions.assertThat(repository.getMembers().equals(members));
    }

    @Test
    void wrongEmailAddressThrowsAnException(){
        String expectedMessage = "The email address that you entered is not valid : @wrongemail";
        try{
            new Member("coco","chocolate","@wrongemail","134");
            fail();
        }catch(EmailAddressIsInvalidException e) {
            Assertions.assertThat(e.getMessage().equals(expectedMessage));
        }
    }

    @Test
    void ExistingAddressThrowsAnException(){
        String expectedMessage = member1.getEmail() + " is already used.";
        try{
            repository.addMember(member1);
            repository.addMember(member1);
            fail();
        }catch(EmailNotUniqueException e) {
            Assertions.assertThat(e.getMessage().equals(expectedMessage));
        }
    }

    @Test
    void If_MemberWithSameEmail_AndDifferentInss_ThrowsAnException(){
        String expectedMessage = member1.getEmail() + " is already used.";
        Member member3 = new Member(member1.getFirstName(), member1.getLastName(), member1.getEmail(),"50");
        try{
            repository.addMember(member1);
            repository.addMember(member3);
            fail();
        }catch(EmailNotUniqueException e) {
            Assertions.assertThat(e.getMessage().equals(expectedMessage));
        }
    }

    @Test
    void If_MemeberWithSameInss_AndDifferentEmail_ThrowsAnException(){
        String expectedMessage = member1.getEmail() + " is already used.";
        Member member3 = new Member(member1.getFirstName(), member1.getLastName(), "email@outlook.com", member1.getInss());
        try{
            repository.addMember(member1);
            repository.addMember(member3);
            fail();
        }catch(InssNotUniqueException e) {
            Assertions.assertThat(e.getMessage().equals(expectedMessage));
        }

    }
}
