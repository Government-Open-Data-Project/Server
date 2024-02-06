package com.springboot.government_data_project.service;

import com.springboot.government_data_project.domain.Profile;
import com.springboot.government_data_project.dto.userProfile.UserProfileUpdateDTO;
import com.springboot.government_data_project.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProfileService {

    @Autowired
    private ProfileRepository profileRepository;

    public Profile saveOrUpdateUserProfile(String userId, UserProfileUpdateDTO userProfileUpdateDTO) {
        Profile profile = profileRepository.findByUserId(userId)
                .orElse(new Profile()); // 새 프로필 객체 생성 또는 기존 프로필 찾기

        // DTO에서 엔티티로 데이터 복사
        profile.setUserId(userId);
        profile.setAge(userProfileUpdateDTO.getAge());
        profile.setIsMarried(userProfileUpdateDTO.getIsMarried());
        profile.setRegion(userProfileUpdateDTO.getRegion());
        profile.setPosition(userProfileUpdateDTO.getPosition());
        profile.setInterests(userProfileUpdateDTO.getInterests()); // 관심사 필드 처리

        // 프로필 정보 저장
        return profileRepository.save(profile);
    }
}
