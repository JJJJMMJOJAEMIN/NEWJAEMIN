package com.example.login.dto;

import com.example.login.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MemberDTO {

    private Long idx;
    private String id;
    private String pw;
    private String name;
    private String email;
    private String roles;
    private String filename;
    private String filepath;
    private LocalDateTime regdate;

    public Member toEntity() {
        return new Member(idx,id,pw,name,email,roles,filename,filepath,regdate);
    }
}

//회원 가입 정보 값 저장