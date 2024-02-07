package com.springboot.government_data_project.service;

import com.springboot.government_data_project.domain.Member;
import com.springboot.government_data_project.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Year;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {
    @Autowired
    private MemberRepository memberRepository;

    public String join(Member member){
        memberRepository.save(member); //회원저장
        return member.getUserId();
    }

    public Member findByUserId(String userId){
        return memberRepository.findByUserId(userId);
    }

    public int getMemberAgeByUserId(String userId){
        Member member = memberRepository.findByUserId(userId);
        if (member != null) {
            int birthYear = Integer.parseInt(member.getBirthYear());
            int currentYear = Year.now().getValue();

            return currentYear - birthYear;
        } else {
            return 0;
        }
    }
}
