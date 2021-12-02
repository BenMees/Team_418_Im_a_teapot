package com.team418.services;

import com.team418.api.user.MemberMapper;
import com.team418.api.user.dto.MemberDto;
import com.team418.domain.user.Member;
import com.team418.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public List<Member> getMembers() {
        return memberRepository.getMembers().values().stream().toList();
    }

    public void addMember(Member member) {
        memberRepository.addMember(member);
    }

    public List<MemberDto> getMembersAsMemberDtoForViewerPurposes() {
        return getMembers().stream().map(MemberMapper::memberToDtoViewingPurposes).collect(Collectors.toList());
    }
}
