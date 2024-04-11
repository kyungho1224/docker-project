package com.example.dockerproject.domain.token.dto;

import com.example.dockerproject.common.constant.RegisterStatus;
import com.example.dockerproject.domain.store.constant.StoreRole;
import com.example.dockerproject.domain.store.entity.Store;
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
public class StoreDetails implements UserDetails {

    private Long id;
    private String email;
    private String password;
    private StoreRole storeRole;
    private RegisterStatus registerStatus;

    public static StoreDetails of(Store store) {
        return StoreDetails.builder()
          .id(store.getId())
          .email(store.getEmail())
          .password(store.getPassword())
          .storeRole(store.getStoreRole())
          .registerStatus(store.getRegisterStatus())
          .build();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(this.storeRole.toString()));
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
