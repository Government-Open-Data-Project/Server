package com.springboot.government_data_project.controller;

import com.springboot.government_data_project.dto.UserProfileDTO;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/user")
public class UserController {
    /**
     * 프론트 개발을 위해 임시로 만들어 놓은 함수입니다
     * 로그인 및 회원가입 기능이 구현되면 수정이 필요합니다
     */
    @Operation(summary = "유저 프로필 정보 불러오는 기능 / 테스트 단계입니다 ")
    @GetMapping("/profile")
    public ResponseEntity<UserProfileDTO> getUserProfile() {
        UserProfileDTO userProfileDTO = UserProfileDTO.builder()
                .name("김민재")
                .age(25)
                .isMarried(false)
                .region("경북")
                .position("학생")
                .interest("IT")
                .build();

        return ResponseEntity.ok(userProfileDTO);
    }

    @Operation(summary = "프로필 정보를 등록 또는 업데이트 하는 함수 / 테스트 단계입니다 ")
    @PostMapping("/profile")
    public ResponseEntity<UserProfileDTO> createAndSaveUserProfile(@RequestBody UserProfileDTO userProfileDTO) {
        // userService 계층으로 정보 보내는 함수 추가 필요
        // 프론트 구현을 위해 만들어 둔 함수
        return ResponseEntity.status(HttpStatus.CREATED).body(userProfileDTO);
    }
}
