package com.example.dockerproject.domain.token.dto;

import com.example.dockerproject.domain.member.constant.MemberRole;
import com.example.dockerproject.domain.member.constant.RegisterStatus;
import com.example.dockerproject.domain.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class MemberDetails implements UserDetails {

    private Long id;
    private String email;
    private String password;
    private MemberRole memberRole;
    private RegisterStatus registerStatus;

    public static MemberDetails of(Member member) {
        return MemberDetails.builder()
          .id(member.getId())
          .email(member.getEmail())
          .password(member.getPassword())
          .memberRole(member.getMemberRole())
          .registerStatus(member.getRegisterStatus())
          .build();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(this.memberRole.toString()));
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.registerStatus == RegisterStatus.REGISTERED;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.registerStatus == RegisterStatus.REGISTERED;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.registerStatus == RegisterStatus.REGISTERED;
    }

    @Override
    public boolean isEnabled() {
        return this.registerStatus == RegisterStatus.REGISTERED;
    }

}
