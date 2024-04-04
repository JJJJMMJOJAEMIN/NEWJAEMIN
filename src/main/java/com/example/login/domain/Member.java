package com.example.login.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Setter
@Table(name="A_MEMBER")
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "a_m_seq")
    @SequenceGenerator(name = "a_m_seq", sequenceName = "a_m_seq", allocationSize = 1)
    private Long idx; //번호

    private String id; //아이디

    private String pw; //비밀번호

    private String email; //이메일

    private String name; //이름

    private String roles; //권한

    private String filename; //프로필 사진 이름

    private String filepath; //프로필 사진 저장 경로

    private LocalDateTime regdate; //날짜

    @OneToMany(mappedBy = "member")
    private List<Board> boards = new ArrayList<>();

    public Member(Long idx, String id, String pw, String email, String name, String roles, String filename, String filepath, LocalDateTime regdate) {
        this.idx = idx;
        this.id = id;
        this.pw = pw;
        this.email = email;
        this.name = name;
        this.roles = roles;
        this.filename = filename;
        this.filepath = filepath;
        this.regdate = regdate;
    }

    private Member(String userId, String pw, String name, String email , String filename, String filepath , String roleUser) {
        this.id = userId;
        this.pw = pw;
        this.name = name;
        this.email = email;
        this.roles = roleUser;
        this.filename = filename;
        this.filepath = filepath;
        this.regdate = LocalDateTime.now();
    }
    public static Member createUser(String userId, String pw, String name, String email, String filename,String filepath,   PasswordEncoder passwordEncoder) {
        return new Member(userId, passwordEncoder.encode(pw), name, email,filename,filepath,  "USER");
    }

}

//Entity 임 걍 오라클 테이블이랑 매핑