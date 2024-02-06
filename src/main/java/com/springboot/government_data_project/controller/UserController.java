package com.springboot.government_data_project.controller;

import com.springboot.government_data_project.domain.Member;
import com.springboot.government_data_project.domain.Profile;
import com.springboot.government_data_project.dto.userProfile.UserProfileDTO;
import com.springboot.government_data_project.dto.userProfile.UserProfileUpdateDTO;
import com.springboot.government_data_project.repository.MemberRepository;
import com.springboot.government_data_project.repository.ProfileRepository;
import com.springboot.government_data_project.service.ProfileService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("api/user")
public class UserController {

    @Autowired
    ProfileRepository profileRepository;
    @Autowired
    MemberRepository memberRepository;

    @Autowired
    ProfileService profileService;
    @Operation(summary = "유저 프로필 정보 불러오는 기능 / 테스트 단계입니다")
    @GetMapping("/profile")
    public ResponseEntity<?> getUserProfile(Principal principal) {
        String userId = principal.getName();

        // 데이터베이스에서 userId에 해당하는 프로필 정보 조회
        Optional<Profile> profileOpt = profileRepository.findByUserId(userId);

        if (!profileOpt.isPresent()) {
            // 프로필 정보가 없을 경우, 클라이언트에게 에러 메시지 반환
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Profile not found"));
        }

        // 조회된 Profile 엔티티를 UserProfileDTO로 변환
        Profile profile = profileOpt.get();
        UserProfileDTO userProfileDTO = UserProfileDTO.builder()
                .name(profile.getName())
                .age(profile.getAge())
                .isMarried(profile.getIsMarried())
                .region(profile.getRegion())
                .position(profile.getPosition())
                .interests(profile.getInterests()) // 관심사 정보 포함
                .build();

        Optional<Member> member = Optional.ofNullable(memberRepository.findByUserId(userId));
        if(member.isPresent()){
            userProfileDTO.setName(member.get().getName());
        }

        return ResponseEntity.ok(userProfileDTO);
    }


    @Operation(summary = "프로필 정보를 등록 또는 업데이트 하는 함수 / 테스트 단계입니다")
    @PostMapping("/profile")
    public ResponseEntity<UserProfileUpdateDTO> createAndSaveUserProfile(@RequestBody UserProfileUpdateDTO userProfileUpdateDTO, Principal principal) {
        String userId = principal.getName(); // 현재 인증된 사용자의 ID를 가져옴

        // ProfileService를 통해 프로필 정보 저장 또는 업데이트
        profileService.saveOrUpdateUserProfile(userId, userProfileUpdateDTO);

        // 응답 반환
        return ResponseEntity.status(HttpStatus.CREATED).body(userProfileUpdateDTO);
    }
}
