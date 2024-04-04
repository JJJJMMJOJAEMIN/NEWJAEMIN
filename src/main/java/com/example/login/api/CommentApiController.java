package com.example.login.api;

import com.example.login.dto.CommentDTO;
import com.example.login.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CommentApiController {

    private final CommentService commentService;

    public CommentApiController(CommentService commentService){
        this.commentService = commentService;
    }

    @GetMapping("/api/board/view/{boardId}/comment")
    public ResponseEntity<List<CommentDTO>> comments(@PathVariable(value = "boardIdx") Long boardIdx){
        List<CommentDTO> dtos = commentService.comments(boardIdx);

        return ResponseEntity.status(HttpStatus.OK).body(dtos);
    }

    @PostMapping("/api/board/{boardId}/comment")
    public ResponseEntity<CommentDTO> create(@PathVariable Long boardIdx,
                                             @PathVariable String memberId,
                                             @RequestBody CommentDTO dto){
        //서비스 위임
        CommentDTO commentDto = commentService.create(boardIdx, dto,memberId);
        //결과 응답
        return ResponseEntity.status(HttpStatus.OK).body(commentDto);
    }
    @PatchMapping("/api/comment/{memberId}")
    public ResponseEntity<CommentDTO> update(@PathVariable String memberId,
                                             @RequestBody CommentDTO dto){
        //서비스 위임
        CommentDTO updatedDto = commentService.update(memberId,dto);
        //결과 응답
        return ResponseEntity.status(HttpStatus.OK).body(updatedDto);
    }
    @DeleteMapping("/api/comment/{memberId}")
    public ResponseEntity<CommentDTO> delete(@PathVariable Long idx){
        
        //서비스 위임
        CommentDTO deletedDto = commentService.delete(idx);
        //결과 응답
        return ResponseEntity.status(HttpStatus.OK).body(deletedDto);
    }

}
