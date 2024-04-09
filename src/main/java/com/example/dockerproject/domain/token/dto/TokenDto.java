package com.example.dockerproject.domain.token.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class TokenDto {

    private String email;
    private String accessToken;
    private String refreshToken;

}
