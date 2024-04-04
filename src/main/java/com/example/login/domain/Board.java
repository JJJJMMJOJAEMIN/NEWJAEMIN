package com.example.login.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Table(name="A_BOARD")
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Setter
@Getter
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "A_B_seq")
    @SequenceGenerator(name = "A_B_seq", sequenceName = "A_B_seq", allocationSize = 1)
    private Long idx; //게시물 번호

    //래퍼런스 쓰라고 하심
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id", referencedColumnName = "ID")
    private Member member;

    @OneToMany(mappedBy = "board")
    private List<FileEntity> files;

    private String title; //제목


    /*private String category;*/

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="category_idx")
    private Category category;

    private String content; //내용


    private String name;

    /*private String good; // 추천
    private String notice; //공지*/

    private LocalDateTime regdate; //날짜


    public String getMemberId() {
        return this.getMember().getId();
    }
}

