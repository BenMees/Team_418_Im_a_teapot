package com.team418.repository;

import com.team418.domain.Address;
import com.team418.domain.user.Member;
import com.team418.exception.EmailAddressIsInvalidException;
import com.team418.exception.EmailNotUniqueException;
import com.team418.exception.InssNotUniqueException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.HashMap;

public class MemberRepositoryTest {
    private HashMap<String, Member> members;
    private MemberRepository repository;
    private Member member1;
    private Member member2;

    private Address address;
    @BeforeEach
    void setup() {
        members = new HashMap<>();
        address = new Address("Sesame Street","221B","9900","Leuven");
        member1 = new Member("Holy", "Banjo", "chickchack@hotmail.com", "1345", address);
        member2 = new Member("yloH", "ojnaB", "chackchick@hotmail.com", "1323453445", address);

        members.put(member1.getUniqueId(), member1);
        members.put(member2.getUniqueId(), member2);

        repository = new MemberRepository();
    }

    @Test
    void whenRepository_HasNoMembers_weGetEmptyMapBack() {
        Assertions.assertThat(repository.getMembers().size()).isEqualTo(0);
    }

    @Test
    void addingNewMember_toTheMemberRepository() {
        repository.addMember(member1);
        repository.addMember(member2);

        Assertions.assertThat(repository.getMembers()).isEqualTo(members);
    }

    @Test
    void wrongEmailAddressThrowsAnException() {
        String expectedMessage = "The email address that you entered is not valid : ann.couwbe-outlook.com";

        Assertions.assertThatExceptionOfType(EmailAddressIsInvalidException.class)
                .isThrownBy(() -> new Member("Ann", "Cauwberg", "ann.couwbe-outlook.com", "45", address))
                .withMessage(expectedMessage);
    }


    @Test
    void If_MemberWithSameEmail_AndDifferentInss_ThrowsAnException() {
        String expectedMessage = member1.getEmail() + " is already used.";
        Member member3 = new Member(member1.getFirstName(), member1.getLastName(), member1.getEmail(), "50", address);

        Assertions.assertThatExceptionOfType(EmailNotUniqueException.class)
                .isThrownBy(() -> {
                    repository.addMember(member1);
                    repository.addMember(member3);
                })
                .withMessage(expectedMessage);
    }

    @Test
    void If_MemberWithSameInss_AndDifferentEmail_ThrowsAnException() {
        String expectedMessage = member1.getInss() + " is already used.";
        Member member3 = new Member(member1.getFirstName(), member1.getLastName(), "email@outlook.com", member1.getInss(), address);

        Assertions.assertThatExceptionOfType(InssNotUniqueException.class)
                .isThrownBy(() -> {
                    repository.addMember(member1);
                    repository.addMember(member3);
                })
                .withMessage(expectedMessage);
    }
}
