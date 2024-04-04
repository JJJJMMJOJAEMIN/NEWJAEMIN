package com.example.login.service;

import com.example.login.domain.Member;
import com.example.login.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

public String join (Member member){
    vaildateDuplicateMember(member);
    return member.getId();
}

private void vaildateDuplicateMember(Member member){
    List<Member> findMembers =
            memberRepository.findByName(member.getName());
    if(!findMembers.isEmpty()){
        throw new IllegalStateException("이미 존재하는 회원 입니다");
    }
}


    public Optional<Member> findOne(String userId) {
        return memberRepository.findById(userId);
    }

    public Page<Member> viewList(Pageable pageable) {

        return memberRepository.findAll(pageable);
    }

    public Member boardView(Long idx) {

        return memberRepository.findById(idx).get();
    }

    public void MemberDelete(Long idx) {

        memberRepository.deleteById(idx);
    }

    public void boardModify(Member member) {

        memberRepository.save(member);
    }

}