package com.example.login.service;

import com.example.login.domain.Member;
import com.example.login.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegisterMemberService {
    private final PasswordEncoder passwordEncoder;
    private final MemberRepository repository;


    public Long join(String userid, String pw, String name, String email, String filename, String filepath) {
        Member member = Member.createUser(userid, pw, name, email, filename,filepath, passwordEncoder);
        validateDuplicateMember(member);
        repository.save(member);
        System.out.println("Saved member with idx: " + member.getIdx());

        return member.getIdx();
    }

    private void validateDuplicateMember(Member member) {
        repository.findById(member.getId())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
    }
}