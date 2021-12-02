package com.team418.api.user;

import com.team418.api.user.dto.CreateMemberDto;
import com.team418.api.user.dto.MemberDto;
import com.team418.domain.user.Member;
import com.team418.services.MemberService;
import com.team418.services.security.SecurityService;
import org.apache.tomcat.util.http.parser.Authorization;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static com.team418.domain.Feature.VIEW_ALL_MEMBERS;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;



@RestController
@RequestMapping(path = "/members")
public class MemberController {
    private final MemberService memberService;
    private final SecurityService securityService;


    public MemberController(MemberService memberService, SecurityService securityService) {
        this.memberService = memberService;
        this.securityService = securityService;
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public MemberDto createMember(@RequestBody CreateMemberDto createMemberDto) {
        Member member = MemberMapper.dtoToModel(createMemberDto);
        memberService.addMember(member);
        return MemberMapper.modelToDto(member);
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<MemberDto> getAllMembers(@RequestHeader String authorization) {
        securityService.validate(authorization, VIEW_ALL_MEMBERS);
        return memberService.getMembers().stream().map(MemberMapper::memberToDtoViewingPurposes).collect(Collectors.toList());
    }


}
