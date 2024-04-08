package com.example.dockerproject.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/open-api/members")
@RestController
public class MemberOpenApiController {

    @GetMapping("/welcome")
    public String welcome() {
        return "Welcome~";
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello~";
    }

}
