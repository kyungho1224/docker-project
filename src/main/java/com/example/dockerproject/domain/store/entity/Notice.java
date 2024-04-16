package com.example.dockerproject.domain.store.entity;

import com.example.dockerproject.common.constant.RegisterStatus;
import com.example.dockerproject.common.entity.BaseEntity;
import com.example.dockerproject.domain.store.dto.NoticeDto;
import com.querydsl.core.annotations.QueryEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Table(name = "notices")
@Entity
@QueryEntity
public class Notice extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "store_id")
    private Store store;

    @Column(nullable = false, columnDefinition = "VARCHAR(20) NOT NULL COMMENT '제목'")
    private String title;

    @Column(nullable = false, columnDefinition = "VARCHAR(255) NOT NULL COMMENT '내용'")
    private String message;

    @Column(nullable = false, columnDefinition = "VARCHAR(20) NOT NULL COMMENT '등록 상태'")
    private RegisterStatus registerStatus;

    public static Notice createOf(Store store, NoticeDto.RegisterRequest request) {
        return Notice.builder()
          .store(store)
          .title(request.getTitle())
          .message(request.getMessage())
          .registerStatus(request.getRegisterStatus())
          .build();
    }

    public void updateData(NoticeDto.RegisterRequest request) {
        this.title = request.getTitle();
        this.message = request.getMessage();
    }

    public void delete() {
        this.registerStatus = RegisterStatus.UNREGISTERED;
    }

}
