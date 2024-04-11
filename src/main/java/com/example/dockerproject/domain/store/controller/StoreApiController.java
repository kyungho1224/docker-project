package com.example.dockerproject.domain.store.controller;

import com.example.dockerproject.domain.store.dto.StoreDto;
import com.example.dockerproject.domain.store.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/stores")
@RestController
public class StoreApiController {

    private final StoreService storeService;

    @GetMapping("/me")
    public ResponseEntity<StoreDto.SimpleInfo> me(
      Authentication authentication
    ) {
       var response = storeService.getStore(authentication.getName());
       return ResponseEntity.ok(response);
    }

}
