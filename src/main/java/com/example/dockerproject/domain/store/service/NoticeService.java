package com.example.dockerproject.domain.store.service;

import com.example.dockerproject.common.constant.RegisterStatus;
import com.example.dockerproject.domain.store.dto.NoticeDto;
import com.example.dockerproject.domain.store.entity.Notice;
import com.example.dockerproject.domain.store.entity.Store;
import com.example.dockerproject.domain.store.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class NoticeService {

    private final NoticeRepository noticeRepository;
    private final StoreService storeService;

    public NoticeDto.RegisterResponse create(String email, NoticeDto.RegisterRequest request) {
        Store store = storeService.getStoreOrElseThrow(email);
        Notice newNotice = Notice.createOf(store, request);
        Notice savedNotice = noticeRepository.save(newNotice);
        return NoticeDto.RegisterResponse.of(savedNotice);
    }

    public NoticeDto.DetailInfo selectOne(String email, Long noticeId) {
        storeService.getStoreOrElseThrow(email);
        Notice notice = noticeRepository.findNoticeByIdAndRegisterStatus(noticeId, RegisterStatus.REGISTERED);
        return NoticeDto.DetailInfo.of(notice);
    }

    public Page<NoticeDto.SimpleInfo> selectList(String email, Pageable pageable) {
        storeService.getStoreOrElseThrow(email);
        return noticeRepository.findAllByRegisterStatus(pageable, RegisterStatus.REGISTERED)
          .map(NoticeDto.SimpleInfo::of);
    }

    public NoticeDto.DetailInfo update(String email, Long noticeId, NoticeDto.RegisterRequest request) {
        storeService.getStoreOrElseThrow(email);
        Notice notice = noticeRepository.findNoticeByIdAndRegisterStatus(noticeId, RegisterStatus.REGISTERED);
        notice.updateData(request);
        Notice updatedNotice = noticeRepository.save(notice);
        return NoticeDto.DetailInfo.of(updatedNotice);
    }

    public void delete(String email, Long noticeId) {
        storeService.getStoreOrElseThrow(email);
        Notice notice = noticeRepository.findNoticeByIdAndRegisterStatus(noticeId, RegisterStatus.REGISTERED);
        notice.delete();
    }

}
