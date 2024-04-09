package com.example.dockerproject.domain.member.controller;

import com.example.dockerproject.domain.member.dto.MemberDto;
import com.example.dockerproject.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/open-api/members")
@RestController
public class MemberOpenApiController {

    private final MemberService memberService;

    @GetMapping("/welcome")
    public String welcome() {
        return "Welcome~";
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello~";
    }

    @PostMapping("/join")
    public ResponseEntity<MemberDto.MemberJoinResponse> join(
      @RequestBody MemberDto.MemberJoinRequest request
    ) {
        MemberDto.MemberJoinResponse response = memberService.createMember(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<MemberDto.MemberLoginResponse> login(
      @RequestBody MemberDto.MemberLoginRequest request
    ) {
        var response = memberService.loginMember(request);
        var header = memberService.setHeaderAccessToken(response.getAccessToken());
        return ResponseEntity.ok().headers(header).body(response);
    }

}
