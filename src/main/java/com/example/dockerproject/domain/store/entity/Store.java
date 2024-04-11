package com.example.dockerproject.domain.store.entity;

import com.example.dockerproject.common.constant.RegisterStatus;
import com.example.dockerproject.common.entity.BaseEntity;
import com.example.dockerproject.domain.product.entity.Product;
import com.example.dockerproject.domain.store.constant.StoreRole;
import com.example.dockerproject.domain.store.dto.StoreDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Table(name = "stores")
@Entity
public class Store extends BaseEntity {

    @Column(nullable = false, columnDefinition = "VARCHAR(20) NOT NULL COMMENT '상점명'")
    private String name;

    @Column(columnDefinition = "VARCHAR(255) DEFAULT '안녕하세요' COMMENT '상점 설명'")
    private String description;

    @Column(nullable = false, columnDefinition = "VARCHAR(50) NOT NULL COMMENT '이메일'")
    private String email;

    @Column(nullable = false, columnDefinition = "VARCHAR(255) NOT NULL COMMENT '비밀번호'")
    private String password;

    @Column(nullable = false, columnDefinition = "VARCHAR(15) NOT NULL COMMENT '휴대폰'")
    private String mobile;

    @Column(nullable = false, columnDefinition = "VARCHAR(255) NOT NULL COMMENT '주소'")
    private String address;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "VARCHAR(20) NOT NULL COMMENT '등록 상태'")
    private RegisterStatus registerStatus;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "VARCHAR(20) NOT NULL COMMENT '상점 등급'")
    private StoreRole storeRole;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Product> products = new ArrayList<>();

    public static Store createOf(StoreDto.JoinRequest request, String encryptPassword) {
        return Store.builder()
          .name(request.getName())
          .description(request.getDescription())
          .email(request.getEmail())
          .password(encryptPassword)
          .mobile(request.getMobile())
          .address(request.getAddress())
          .registerStatus(RegisterStatus.REGISTERED)
          .storeRole(StoreRole.JUNIOR)
          .build();
    }

    public void updateRole(StoreRole storeRole) {
        this.storeRole = storeRole;
    }

    public void addProduct(Product product) {
        this.products.add(product);
    }

    public void removeProduct(Product product) {
        this.products.remove(product);
    }

    public void delete() {
        this.registerStatus = RegisterStatus.UNREGISTERED;
    }

}
