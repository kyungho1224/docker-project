package com.example.dockerproject.domain.store.repository;

import com.example.dockerproject.common.constant.RegisterStatus;
import com.example.dockerproject.domain.store.entity.Notice;
import com.example.dockerproject.exception.ApiErrorCode;
import com.example.dockerproject.exception.ApiException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NoticeRepository extends JpaRepository<Notice, Long> {

    Optional<Notice> findByIdAndRegisterStatus(Long id, RegisterStatus registerStatus);

    default Notice findNoticeByIdAndRegisterStatus(Long id, RegisterStatus registerStatus) {
        return findByIdAndRegisterStatus(id, registerStatus)
          .orElseThrow(() -> new ApiException(ApiErrorCode.NOT_FOUND_NOTICE));
    }

    Page<Notice> findAllByRegisterStatus(Pageable pageable, RegisterStatus registerStatus);

}
