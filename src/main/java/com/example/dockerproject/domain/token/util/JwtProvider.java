package com.example.dockerproject.domain.token.util;

import com.example.dockerproject.domain.token.dto.TokenDto;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Slf4j
@Component
public class JwtProvider {

    private final Key key;

    private static final long ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60;
    private static final long REFRESH_TOKEN_EXPIRE_TIME = 1000 * 60 * 3;

    public JwtProvider(
      @Value("${spring.jwt.secret-key}") String secretKey
    ) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public TokenDto generatedToken(String email) {
        long now = (new Date()).getTime();
        Date accessTokenExpireTime = new Date(now + ACCESS_TOKEN_EXPIRE_TIME);
        Date refreshTokenExpireTime = new Date(now + REFRESH_TOKEN_EXPIRE_TIME);
        String accessToken = Jwts.builder()
          .setSubject(email)
          .claim("email", email)
          .setExpiration(accessTokenExpireTime)
          .signWith(key, SignatureAlgorithm.HS256)
          .compact();
        String refreshToken = Jwts.builder()
          .setSubject(email)
          .claim("email", email)
          .setExpiration(refreshTokenExpireTime)
          .signWith(key, SignatureAlgorithm.HS256)
          .compact();
        return new TokenDto(email, accessToken, refreshToken);
    }

    public String getEmailFromToken(String accessToken) {
        // 엑세스토큰에서 subject 에 포함된 이메일을 추출한다
        // 엑세스토큰이 만료되었다면 예외가 발생한다
        String email;
        try {
            JwtParser parser = Jwts.parserBuilder().setSigningKey(key).build();
            Claims claims = parser.parseClaimsJws(accessToken).getBody();
            email = claims.getSubject();
        } catch (Exception e) {
            if (e instanceof ExpiredJwtException) {
                log.error("getEmailFromToken ExpiredJwtException e : {}", e.getMessage());
            } else if (e instanceof MalformedJwtException) {
                log.error("getEmailFromToken MalformedJwtException e : {}", e.getMessage());
            } else if (e instanceof UnsupportedJwtException) {
                log.error("getEmailFromToken UnsupportedJwtException e : {}", e.getMessage());
            } else {
                log.error("getEmailFromToken else e : {}", e.getMessage());
            }
            throw new RuntimeException("getEmailFromToken exception");
        }
        return email;
    }

    public String validateToken(String accessToken) {
        try {
            JwtParser parser = Jwts.parserBuilder().setSigningKey(key).build();
            Claims claims = parser.parseClaimsJws(accessToken).getBody();
            return String.valueOf(claims.get("email"));
        } catch (Exception e) {
            if (e instanceof ExpiredJwtException) {
                log.error("getEmailFromToken ExpiredJwtException e : {}", e.getMessage());
            } else if (e instanceof MalformedJwtException) {
                log.error("getEmailFromToken MalformedJwtException e : {}", e.getMessage());
            } else if (e instanceof UnsupportedJwtException) {
                log.error("getEmailFromToken UnsupportedJwtException e : {}", e.getMessage());
            } else {
                log.error("getEmailFromToken else e : {}", e.getMessage());
            }
            throw new RuntimeException("getEmailFromToken exception");
        }
    }

    public boolean isTokenExpire(String token) {
        // expired = true, validate = false;
        try {
            JwtParser parser = Jwts.parserBuilder().setSigningKey(key).build();
            Claims claims = parser.parseClaimsJws(token).getBody();
            return claims.getExpiration().before(new Date());
        } catch (Exception e) {
            if (e instanceof ExpiredJwtException) {
                log.error("getEmailFromToken ExpiredJwtException e : {}", e.getMessage());
            } else if (e instanceof MalformedJwtException) {
                log.error("getEmailFromToken MalformedJwtException e : {}", e.getMessage());
            } else if (e instanceof UnsupportedJwtException) {
                log.error("getEmailFromToken UnsupportedJwtException e : {}", e.getMessage());
            } else {
                log.error("getEmailFromToken else e : {}", e.getMessage());
            }
        }
        return true;
    }

}
