package com.springboot.government_data_project.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
@Slf4j
public class JwtFilter extends OncePerRequestFilter {

    @Value("${jwt.secret_key}")
    private final String secretKey;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
        log.info("authorization : {}", authorization);

        // 토큰이 형식 검사
        if (!StringUtils.hasText(authorization) || !authorization.startsWith("Bearer ")) {
            log.error("authorization을 잘못 보냈습니다.");
            filterChain.doFilter(request, response);
            return;
        }


        // 토큰 꺼내기
        String token = authorization.split(" ")[1];
        log.info("token = {}", token);
        log.info("secret = {}", secretKey);

        // 토큰 만료 여부
        if (JwtUtil.isExpired(token, secretKey)) {
            log.error("토큰이 만료되었습니다");
            filterChain.doFilter(request, response);
            return;
        }

        // 토큰에서 id 꺼내기
        String userId = JwtUtil.getUserId(token, secretKey);
        log.info("userId = {}", userId);


        // 권한 부여 - 따로 권한 없음
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(
                        userId,
                        null,
                        null); // 생성자를 살펴보니 (arg1, arg2) 는 authenticated false로 만들어지고  (arg1, 2, 3) 은 true로 생성

        // Detail 넣기
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        log.info(authenticationToken.getName());
        filterChain.doFilter(request, response); // 필터체인에 request 넘기면 인증 사인이 찍힘 -> 다음으로 넘어가면 통과
    }


}
