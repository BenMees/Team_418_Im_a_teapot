package com.team418.api.user;

import com.team418.api.user.dto.CreateMemberDto;
import com.team418.api.user.dto.MemberDto;
import com.team418.domain.user.Member;

public class MemberMapper {
    public static MemberDto modelToDto(Member member) {
        return new MemberDto(member.getUniqueId(), member.getFirstName(), member.getLastName(), member.getEmail(), member.getInss());
    }

    public static Member dtoToModel(CreateMemberDto memberDto) {
        return new Member(memberDto.firstName(), memberDto.lastName(), memberDto.email(), memberDto.inss());
    }
}
