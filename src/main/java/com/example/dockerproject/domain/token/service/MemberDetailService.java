package com.example.dockerproject.domain.token.service;

import com.example.dockerproject.domain.member.entity.Member;
import com.example.dockerproject.domain.member.repository.MemberRepository;
import com.example.dockerproject.domain.token.dto.MemberDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MemberDetailService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(username)
          .orElseThrow(() -> new RuntimeException("Not Found Member"));
        return MemberDetails.of(member);
    }

}
