package com.example.dockerproject.domain.store.service;

import com.example.dockerproject.common.constant.RegisterStatus;
import com.example.dockerproject.domain.store.dto.StoreDto;
import com.example.dockerproject.domain.store.entity.Store;
import com.example.dockerproject.domain.store.repository.StoreRepository;
import com.example.dockerproject.domain.token.dto.TokenDto;
import com.example.dockerproject.domain.token.repository.RefreshTokenRepository;
import com.example.dockerproject.domain.token.util.JwtProvider;
import com.example.dockerproject.exception.ApiErrorCode;
import com.example.dockerproject.exception.ApiException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class StoreService {

    private final StoreRepository storeRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    public StoreDto.JoinResponse createStore(StoreDto.JoinRequest request) {
        Store newStore = Store.createOf(request, passwordEncoder.encode(request.getPassword()));
        Store savedStore = storeRepository.save(newStore);
        return StoreDto.JoinResponse.of(savedStore);
    }

    public StoreDto.LoginResponse loginStore(StoreDto.LoginRequest request) {
        return storeRepository.findByEmailAndRegisterStatus(request.getEmail(), RegisterStatus.REGISTERED)
          .filter(store -> passwordEncoder.matches(request.getPassword(), store.getPassword()))
          .map(store -> {
              TokenDto tokenDto = jwtProvider.generatedToken(store.getEmail());
              refreshTokenRepository.setRefreshToken(tokenDto);
              return StoreDto.LoginResponse.of(store, tokenDto.getAccessToken());
          })
          .orElseThrow(() -> new RuntimeException("Not Found Store"));
    }

    public HttpHeaders setHeaderAccessToken(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        String tokenPrefix = "Bearer ";
        headers.set(HttpHeaders.AUTHORIZATION, String.format("%s%s", tokenPrefix, accessToken));
        return headers;
    }

    public StoreDto.SimpleInfo getStore(String email) {
        return storeRepository.findByEmailAndRegisterStatus(email, RegisterStatus.REGISTERED)
          .map(StoreDto.SimpleInfo::of)
          .orElseThrow(() -> new RuntimeException("Not Found Store"));
    }

    public Store getStoreOrElseThrow(String email) {
        return storeRepository.findByEmailAndRegisterStatus(email, RegisterStatus.REGISTERED)
          .orElseThrow(() -> new ApiException(ApiErrorCode.NOT_FOUND_STORE));
    }

}
