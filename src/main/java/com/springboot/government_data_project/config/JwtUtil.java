package com.springboot.government_data_project.config;

import java.util.Date;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JwtUtil {
    public static String createJwt(String userId, String secretkey, Long expiredMs) {
        Claims claims = Jwts.claims();  // 정보 저장용
        claims.put("userId", userId);   // 정보 추가

        System.out.println("jwt 생성, secret : " + secretkey);

        return Jwts.builder()
                .setClaims(claims)  // 클레임 설정
                .setIssuedAt(new Date(System.currentTimeMillis()))  // 생성 시간
                .setExpiration(new Date(System.currentTimeMillis() + expiredMs)) // 만료 시간
                .signWith(SignatureAlgorithm.HS256, secretkey) // HS256 알고리즘 사용, 시크릿키로 서명
                .compact();
    }

    public static boolean isExpired(String token, String secretKey) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)   // 시크릿 키 설정
                .build()
                .parseClaimsJws(token) // 클레임 파싱
                .getBody()
                .getExpiration()    // 만료 시간
                .before(new Date(System.currentTimeMillis()));
    }

    public static String getUserId(String token, String secretKey) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)   // 시크릿 키 설정
                .build()
                .parseClaimsJws(token) // 클레임 파싱
                .getBody()
                .get("userId", String.class);
    }

    // 토큰 검증
    public static boolean isValid(String token, String secretKey) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token);
        } catch (JwtException e) {
            return false;
        }
        return true;
    }
}
