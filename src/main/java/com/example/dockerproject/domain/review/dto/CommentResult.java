package com.example.dockerproject.domain.review.dto;

import java.time.LocalDateTime;

public interface CommentResult {

    Long getId();
    String getContents();
    String getWriter();
    LocalDateTime getCreatedAt();

}
