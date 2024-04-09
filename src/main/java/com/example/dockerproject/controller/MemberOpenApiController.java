package com.example.dockerproject.controller;

import com.example.dockerproject.dto.MemberDto;
import com.example.dockerproject.service.MemberService;
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

}
