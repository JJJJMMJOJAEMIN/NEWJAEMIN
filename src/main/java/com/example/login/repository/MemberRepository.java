package com.example.login.repository;

import com.example.login.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findById(String userId);
    List<Member> findByName(String name);
}

//정보 저장