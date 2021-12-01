package com.team418.api.user;

import com.team418.api.user.dto.CreateMemberDto;
import com.team418.api.user.dto.MemberDto;
import com.team418.domain.user.Member;
import com.team418.services.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = "users/members")
public class MemberController {
    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public MemberDto createMember(@RequestBody CreateMemberDto createMemberDto) {
        Member member = MemberMapper.dtoToModel(createMemberDto);
        memberService.addMember(member);
        return MemberMapper.modelToDto(member);
    }


}
