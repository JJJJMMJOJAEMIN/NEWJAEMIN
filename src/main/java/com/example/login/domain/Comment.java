package com.example.login.domain;


import com.example.login.dto.CommentDTO;
import jakarta.persistence.*;
import lombok.*;

@Table(name="A_COMMENT")
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "a_m_seq")
    @SequenceGenerator(name = "a_m_seq", sequenceName = "a_m_seq", allocationSize = 1)
    private Long idx; //번호

    @ManyToOne
    @JoinColumn(name="member_id",referencedColumnName="id")
    private Member member;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="board_idx")
    private Board board;

    private String content; //댓글 내용

    public static Comment createComment(CommentDTO dto, Board board,Member member) {

        //예외 발생
        if(dto.getIdx() != null)
            throw new IllegalArgumentException("댓글 생성 실패! 댓글의 id가 없어야 합니다.");
        if(dto.getBoardIdx() != board.getIdx())
            throw new IllegalArgumentException("댓글 생성 실패! 게시글의 id가 잘못됐습니다.");
        //엔티티 생성 및 반환
        return new Comment(
                dto.getIdx(), //댓글 아이디
                member,     //부모 게시글
                board, //댓글 닉네임
                dto.getContent() //댓글 본문
        );
    }
    public void patch(CommentDTO dto) {
        //예외 발생
        if(this.idx != dto.getIdx())
            throw new IllegalArgumentException("댓글 수정 실패! 잘못된 id가 입력 됐습니다.");
        if(dto.getContent() != null) //수정할 본문 데이터가 있다면
            this.content = dto.getContent(); //내용 반영
    }
}
