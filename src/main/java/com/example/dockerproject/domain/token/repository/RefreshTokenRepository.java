package com.example.dockerproject.domain.token.repository;

import com.example.dockerproject.domain.token.dto.TokenDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Repository
public class RefreshTokenRepository {

    private final RedisTemplate<String, TokenDto> refreshTokenRepository;
    private final static Duration REFRESH_TOKEN_CACHE_TTL = Duration.ofMinutes(3);

    public void setRefreshToken(TokenDto tokenDto) {
        String key = getKey(tokenDto.getAccessToken());
        refreshTokenRepository.opsForValue().set(key, tokenDto, REFRESH_TOKEN_CACHE_TTL);
    }

    public Optional<TokenDto> getRefreshToken(String accessToken) {
        String key = getKey(accessToken);
        return Optional.ofNullable(refreshTokenRepository.opsForValue().get(key));
    }

    public void deleteRefreshToken(String accessToken) {
        String key = getKey(accessToken);
        refreshTokenRepository.delete(key);
    }

    private String getKey(String accessToken) {
        return "access:" + accessToken;
    }

}
