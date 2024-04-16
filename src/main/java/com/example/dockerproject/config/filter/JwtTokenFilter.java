package com.example.dockerproject.config.filter;

import com.example.dockerproject.domain.token.dto.TokenDto;
import com.example.dockerproject.domain.token.repository.RefreshTokenRepository;
import com.example.dockerproject.domain.token.service.MemberDetailService;
import com.example.dockerproject.domain.token.service.StoreDetailService;
import com.example.dockerproject.domain.token.util.JwtProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtTokenFilter extends OncePerRequestFilter {
    // UsernamePasswordAuthenticationFilter
    private final JwtProvider jwtProvider;
    private final RefreshTokenRepository refreshTokenRepository;
    private final MemberDetailService memberDetailService;
    private final StoreDetailService storeDetailService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (header != null) {
            String accessToken = header.split(" ")[1].trim();
            boolean isExpired = jwtProvider.isTokenExpire(accessToken);
            if (!isExpired) {
                String email = jwtProvider.validateToken(accessToken);
                UsernamePasswordAuthenticationToken authentication;
                UserDetails user;
                if (isMemberRequest(request)) {
                    user = memberDetailService.loadUserByUsername(email);
                } else {
                    user = storeDetailService.loadUserByUsername(email);
                }
                authentication = new UsernamePasswordAuthenticationToken(
                  user, null, user.getAuthorities()
                );
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                log.error("doFilterInternal 엑세스 토큰 만료");
                refreshTokenRepository.getRefreshToken(accessToken)
                  .ifPresentOrElse(refresh -> {
                        // TODO 이전 엑세스 토큰을 키로 가지고 있는 redis 데이터 삭제 추가하기
                        refreshTokenRepository.deleteRefreshToken(accessToken);
                        TokenDto tokenDto = jwtProvider.generatedToken(refresh.getEmail());
                        refreshTokenRepository.setRefreshToken(tokenDto);
                        response.setHeader(HttpHeaders.AUTHORIZATION, String.format("Bearer %s", tokenDto.getAccessToken()));
                    },
                    () -> log.error("doFilterInternal 리프레시 토큰 만료"));
            }
        } else {
            log.error("header null");
        }

        filterChain.doFilter(request, response);
    }

    private boolean isMemberRequest(HttpServletRequest request) {
        String requestUri = request.getRequestURI();
        return requestUri.contains("members") || requestUri.contains("reviews");
    }

}
