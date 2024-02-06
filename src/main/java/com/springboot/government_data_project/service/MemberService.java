package com.springboot.government_data_project.service;

import com.springboot.government_data_project.domain.Member;
import com.springboot.government_data_project.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public String join(Member member){
        memberRepository.save(member); //회원저장
        return member.getUserId();
    }

    public Member findByUserId(String userId){
        return memberRepository.findByUserId(userId);
    }
}
