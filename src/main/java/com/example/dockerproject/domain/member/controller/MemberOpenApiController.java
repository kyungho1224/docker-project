package com.example.dockerproject.domain.member.controller;

import com.example.dockerproject.domain.member.dto.MemberDto;
import com.example.dockerproject.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/open-api/members")
@RestController
public class MemberOpenApiController {

    private final MemberService memberService;

    @PostMapping("/register")
    public ResponseEntity<MemberDto.JoinResponse> register(
      @Validated
      @RequestBody MemberDto.JoinRequest request
    ) {
        var response = memberService.createMember(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<MemberDto.LoginResponse> login(
      @RequestBody MemberDto.LoginRequest request
    ) {
        var response = memberService.loginMember(request);
        var header = memberService.setHeaderAccessToken(response.getAccessToken());
        return ResponseEntity.ok().headers(header).body(response);
    }

}
