package com.example.dockerproject.domain.store.repository;

import com.example.dockerproject.common.constant.RegisterStatus;
import com.example.dockerproject.domain.store.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StoreRepository extends JpaRepository<Store, Long> {

    Optional<Store> findByEmailAndRegisterStatus(String email, RegisterStatus registerStatus);

}
