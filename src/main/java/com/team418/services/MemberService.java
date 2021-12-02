package com.team418.services;

import com.team418.domain.user.Member;
import com.team418.repository.MemberRepository;
import org.springframework.stereotype.Service;

@Service
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public void addMember(Member member) {
        memberRepository.addMember(member);
    }

    public Member getMemberByInss(String inss) {
        return memberRepository.getMemberByInss(inss);
    }
}
