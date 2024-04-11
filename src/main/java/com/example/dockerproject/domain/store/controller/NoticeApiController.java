package com.example.dockerproject.domain.store.controller;

import com.example.dockerproject.domain.store.dto.NoticeDto;
import com.example.dockerproject.domain.store.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.NO_CONTENT;

@RequiredArgsConstructor
@RequestMapping("/api/stores/notices")
@RestController
public class NoticeApiController {

    private final NoticeService noticeService;

    @PostMapping("/register")
    public ResponseEntity<NoticeDto.RegisterResponse> register(
      Authentication authentication,
      @Validated
      @RequestBody NoticeDto.RegisterRequest request
    ) {
        var response = noticeService.create(authentication.getName(), request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{noticeId}")
    public ResponseEntity<NoticeDto.DetailInfo> getOne(
      Authentication authentication,
      @PathVariable Long noticeId
    ) {
        return null;
    }

    @GetMapping
    public ResponseEntity<Page<NoticeDto.SimpleInfo>> getAll(
      Authentication authentication,
      Pageable pageable
    ) {
        return null;
    }

    @PatchMapping("/{noticeId}")
    public ResponseEntity<NoticeDto.DetailInfo> modify(
      Authentication authentication,
      @PathVariable Long noticeId,
      @RequestBody NoticeDto.RegisterRequest request
    ) {
        return null;
    }

    @DeleteMapping("/{noticeId}")
    public ResponseEntity<Void> unregister(
      Authentication authentication,
      @PathVariable Long noticeId
    ) {
        return ResponseEntity.status(NO_CONTENT).build();
    }

}
