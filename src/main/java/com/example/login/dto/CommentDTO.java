package com.example.login.dto;

import com.example.login.domain.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO {

    private Long idx;

    private Long boardIdx;

    private String memberId; //멤버의 id

    private String content; // 댓글의 내용

     public static CommentDTO createCommentDto(Comment comment) {
        return new CommentDTO(
                comment.getIdx(),
                comment.getBoard().getIdx(),
                comment.getMember().getId(),
                comment.getContent()
        );
    }
}
