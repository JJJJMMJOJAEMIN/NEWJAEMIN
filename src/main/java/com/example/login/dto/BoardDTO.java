package com.example.login.dto;

import com.example.login.domain.Board;
import com.example.login.domain.Member;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class BoardDTO {

    private Long idx;
    private String memberId;
    private String title;
    private String content;
    private String name;
/*    private String good;
    private String notice;*/
    private String category;
    private LocalDateTime regdate;


     public Board toEntity(Member member) {
        return Board.builder()
                .idx(this.idx)
                .member(member)
                .title(this.title)
                .content(this.content)
                .name(this.name)
/*                .category(this.category)*/
       /*         .good(this.good)
                .notice(this.notice)*/
                .regdate(LocalDateTime.now())
                .build();
    }
    public void setMemberId(String memberId) {
        this.memberId = memberId;
        System.out.println("BoardDTO memberId: " + memberId); // memberId 값 출력
    }
}

