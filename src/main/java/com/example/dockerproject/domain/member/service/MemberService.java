package com.example.dockerproject.domain.member.service;

import com.example.dockerproject.domain.member.dto.MemberDto;
import com.example.dockerproject.domain.member.entity.Member;
import com.example.dockerproject.domain.member.repository.MemberRepository;
import com.example.dockerproject.domain.token.dto.TokenDto;
import com.example.dockerproject.domain.token.repository.RefreshTokenRepository;
import com.example.dockerproject.domain.token.util.JwtProvider;
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
public class MemberService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    public MemberDto.JoinResponse createMember(MemberDto.JoinRequest request) {
        Member newMember = Member.createOf(request, passwordEncoder.encode(request.getPassword()));
        Member savedMember = memberRepository.save(newMember);
        return MemberDto.JoinResponse.of(savedMember);
    }

    public MemberDto.LoginResponse loginMember(MemberDto.LoginRequest request) {
        log.info("MemberService loginMember");
        // 비밀번호 체크
        // 토큰 생성
        // 리프레시 토큰 저장
        // 엑세스 토큰 포함 반환
        return memberRepository.findByEmail(request.getEmail())
          .filter(member -> passwordEncoder.matches(request.getPassword(), member.getPassword()))
          .map(member -> {

              TokenDto tokenDto = jwtProvider.generatedToken(member.getEmail());
              refreshTokenRepository.setRefreshToken(tokenDto);
              log.info("MemberService login access : {}", tokenDto.getAccessToken());
              log.info("MemberService login refresh : {}", tokenDto.getRefreshToken());
              return MemberDto.LoginResponse.of(member, tokenDto.getAccessToken());

          })
          .orElseThrow(() -> new RuntimeException("Not Found Member"));
    }

    public HttpHeaders setHeaderAccessToken(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, String.format("Bearer %s", accessToken));
        return headers;
    }

    public Member getMember(String email) {
        return memberRepository.findByEmail(email)
          .orElseThrow(() -> new RuntimeException("Not Found Member"));
    }

}
