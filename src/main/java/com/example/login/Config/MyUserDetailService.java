package com.example.login.Config;

import com.example.login.domain.Member;
import com.example.login.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

//어노테이션은 코드에 추가하는 특별한 표식

@Component // Spring이 이 클래스를 빈(Bean)으로 관리하도록 하는 어노테이션
public class MyUserDetailService implements UserDetailsService {
    private final MemberService memberService;

    @Autowired // 생성자 주입을 위한 어노테이션 (표식)
    public MyUserDetailService(MemberService memberService) {
        this.memberService = memberService;
    }

    @Override
    public UserDetails loadUserByUsername(String insertedUserId) throws UsernameNotFoundException {
        // 사용자 ID로 회원 정보를 조회하여 Optional 객체로 반환
        Optional<Member> findOne = memberService.findOne(insertedUserId);
        // Optional 객체에서 회원 정보를 가져오거나, 회원이 없으면 예외 발생
        Member member = findOne.orElseThrow(() -> new UsernameNotFoundException("없는 회원입니다 ㅠ"));

        // Spring Security의 UserDetails 객체를 생성하여 반환
        return User.builder()
                .username(member.getId()) //아이디
                .password(member.getPw()) //비번
                .roles(member.getRoles()) //사용자 권한 설정
                .build(); //UserDetails 객체 생성
    }
}
