package com.springboot.government_data_project.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain configure(HttpSecurity http)throws Exception{

        http
                .csrf(Customizer.withDefaults())
                .authorizeHttpRequests(authorizeRequests ->
                        //gpt는 인증 필요
                        authorizeRequests.anyRequest().permitAll() //모든 링크의 요청 승인(개발 동안만)
                )
                .httpBasic(Customizer.withDefaults())
                .formLogin(Customizer.withDefaults());
        return http.build();
    }
}
