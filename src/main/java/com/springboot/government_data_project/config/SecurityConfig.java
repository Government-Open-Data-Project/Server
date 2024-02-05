package com.springboot.government_data_project.config;

import com.springboot.government_data_project.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final MemberService memberService;

    @Value("${jwt.secret_key}")
    private String jwtSecretKey;

    @Bean
    public SecurityFilterChain configure(HttpSecurity http)throws Exception{

        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorizeRequests ->
                        //gpt는 인증 필요
                        authorizeRequests.requestMatchers("/api/v1/login/oauth").permitAll() //모든 링크의 요청 승인(개발 동안만)
                                .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults())
                .formLogin(Customizer.withDefaults())
                .addFilterBefore(new JwtFilter(jwtSecretKey), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
