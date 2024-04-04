package com.example.login.service;

import com.example.login.domain.Board;
import com.example.login.domain.Comment;
import com.example.login.domain.Member;
import com.example.login.dto.CommentDTO;
import com.example.login.repository.BoardRepository;
import com.example.login.repository.CommentRepository;
import com.example.login.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {


    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;

    public List<CommentDTO> comments(Long boardIdx) {
        //1. 댓글조회
        List<Comment> comments = commentRepository.findByBoardId(boardIdx);
        //2. 엔티티 -> DTO 변환
        List<CommentDTO> dtos = new ArrayList<CommentDTO>();
        for(int i = 0; i < comments.size(); i++){
            Comment c = comments.get(i);
            CommentDTO dto = CommentDTO.createCommentDto(c);
            dtos.add(dto);
        }
        //3. 결과 변환
        return dtos;
    /*    return commentRepository.findByArticleId(articleId).stream()
                .map(comment -> CommentDto.createCommentDto(comment))
                .collect(Collectors.toList());*/
    }

    //댓글 생성
    @Transactional
    public CommentDTO create(Long boardIdx, CommentDTO dto,String userid) {
        //1. 게시물 조회 및 예외 발생
        Member member = memberRepository.findById(userid)
                .orElseThrow(()-> new IllegalArgumentException("댓글 생성 실패! " +
                        "대상 게시글이 없습니다."));

        Board board = boardRepository.findById(boardIdx)
                .orElseThrow(()-> new IllegalArgumentException("댓글 생성 실패! " +
                        "대상 게시글이 없습니다."));
        //2. 댓글 엔티티 생성
        Comment comment = Comment.createComment(dto, board,member);
        //3. 댓글 엔티티를 DB에 저장
        Comment created =  commentRepository.save(comment);
        //4. DTO로 변환해 반환
        return  CommentDTO.createCommentDto(created);
    }

    //댓글 수정
    @Transactional
    public CommentDTO update(String memberId, CommentDTO dto) {
        //1. 댓글 조회 및 예외 발생
        Comment target = commentRepository.findByMember_Id(memberId)
                .orElseThrow(()-> new IllegalArgumentException("댓글 수정 실패!" +
                        "대상 댓글이 없습니다.!"));
        //2. 댓글 수정
        target.patch(dto);
        //3. DB로 갱신
        Comment updated = commentRepository.save(target);
        //4. 댓글 엔티티를 DTO로 변환 및 반환
        return CommentDTO.createCommentDto(updated);
    }
    //댓글 삭제
    @Transactional
    public CommentDTO delete(Long idx) {

        //1. 댓글 조회 및 예외 발생
        Comment target = commentRepository.findById(idx)
                .orElseThrow(()-> new IllegalArgumentException("댓글 삭제 실패! " +
                        "대상이 없습니다."));
        //2. 댓글 삭제
        commentRepository.delete(target);
        //3. 삭제 댓글을 DTO로 변환 및 반환
        return CommentDTO.createCommentDto(target);
    }
}