package com.example.dockerproject.domain.product.dto;

import com.example.dockerproject.common.constant.RegisterStatus;
import com.example.dockerproject.domain.product.entity.Product;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class ProductDto {

    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Getter
    public static class RegisterRequest {

        private Long storeId;

        private String name;

        private String description;

        private Integer quantity;

        private Integer amount;

        private Float discount;

        private RegisterStatus registerStatus;

        public static RegisterRequest of(Product product) {
            return RegisterRequest.builder()
              .storeId(product.getStore().getId())
              .name(product.getName())
              .description(product.getDescription())
              .quantity(product.getQuantity())
              .amount(product.getAmount())
              .discount(product.getDiscount())
              .build();
        }

    }

}
