package com.example.dockerproject.domain.member.controller;

import com.example.dockerproject.domain.member.dto.MemberDto;
import com.example.dockerproject.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/members")
@RestController
public class MemberApiController {

    private final MemberService memberService;

    @GetMapping("/me")
    public ResponseEntity<MemberDto.SimpleInfo> me(
      Authentication authentication
    ) {
        var response = memberService.getMember(authentication.getName());
        return ResponseEntity.ok(MemberDto.SimpleInfo.of(response));
    }

}
