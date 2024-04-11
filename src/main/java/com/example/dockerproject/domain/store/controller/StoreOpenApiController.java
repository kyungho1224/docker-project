package com.example.dockerproject.domain.store.controller;

import com.example.dockerproject.domain.store.dto.StoreDto;
import com.example.dockerproject.domain.store.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/open-api/stores")
@RestController
public class StoreOpenApiController {

    private final StoreService storeService;

    @PostMapping("/register")
    public ResponseEntity<StoreDto.JoinResponse> register(
      @Validated
      @RequestBody StoreDto.JoinRequest request
    ) {
        var response = storeService.createStore(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<StoreDto.LoginResponse> login(
      @Validated
      @RequestBody StoreDto.LoginRequest request
    ) {
        var response = storeService.loginStore(request);
        var header = storeService.setHeaderAccessToken(response.getAccessToken());
        return ResponseEntity.ok().headers(header).body(response);
    }

}
