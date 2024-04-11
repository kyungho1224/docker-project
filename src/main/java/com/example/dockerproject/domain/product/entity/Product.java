package com.example.dockerproject.domain.product.entity;

import com.example.dockerproject.common.constant.RegisterStatus;
import com.example.dockerproject.common.entity.BaseEntity;
import com.example.dockerproject.domain.product.dto.ProductDto;
import com.example.dockerproject.domain.store.entity.Store;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Table(name = "products")
@Entity
public class Product extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "store_id")
    private Store store;

    @Column(nullable = false, columnDefinition = "VARCHAR(20) NOT NULL COMMENT '상점명'")
    private String name;

    @Column(columnDefinition = "VARCHAR(255) DEFAULT '안녕하세요' COMMENT '상점 설명'")
    private String description;

    @Column(nullable = false, columnDefinition = "INT NOT NULL COMMENT '재고수량'")
    private Integer quantity;

    @Column(nullable = false, columnDefinition = "INT NOT NULL COMMENT '판매금액'")
    private Integer amount;

    @Column(columnDefinition = "FLOAT default 0 COMMENT '할인율'")
    private Float discount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "VARCHAR(20) NOT NULL COMMENT '등록 상태'")
    private RegisterStatus registerStatus;

    @Column(columnDefinition = "VARCHAR(255) DEFAULT NULL COMMENT '대표 이미지'")
    private String mainThumbnail;

    @Column(columnDefinition = "VARCHAR(255) DEFAULT NULL COMMENT '상세 이미지'")
    private String detailThumbnail;

    public static Product createOf(Store store, ProductDto.RegisterRequest request) {
        return Product.builder()
          .store(store)
          .name(request.getName())
          .description(request.getDescription())
          .quantity(request.getQuantity())
          .amount(request.getAmount())
          .discount(request.getDiscount())
          .registerStatus(request.getRegisterStatus())
          .build();
    }

}
