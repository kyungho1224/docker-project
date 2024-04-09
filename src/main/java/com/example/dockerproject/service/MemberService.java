package com.example.dockerproject.service;

import com.example.dockerproject.dto.MemberDto;
import com.example.dockerproject.entity.Member;
import com.example.dockerproject.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberDto.MemberJoinResponse createMember(MemberDto.MemberJoinRequest request) {
        Member newMember = Member.createOf(request);
        Member savedMember = memberRepository.save(newMember);
        return MemberDto.MemberJoinResponse.of(savedMember);
    }

}
