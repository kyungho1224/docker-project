package com.example.dockerproject.domain.token.service;

import com.example.dockerproject.common.constant.RegisterStatus;
import com.example.dockerproject.domain.store.entity.Store;
import com.example.dockerproject.domain.store.repository.StoreRepository;
import com.example.dockerproject.domain.token.dto.StoreDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class StoreDetailService implements UserDetailsService {

    private final StoreRepository storeRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Store store = storeRepository.findByEmailAndRegisterStatus(username, RegisterStatus.REGISTERED)
          .orElseThrow(() -> new RuntimeException("Not Found Store"));
        return StoreDetails.of(store);
    }

}
