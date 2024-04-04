package com.example.login.repository;

import com.example.login.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query(value = "SELECT c FROM Comment c join c.board b WHERE b.idx = :boardIdx")
    List<Comment> findByBoardId(@Param("boardIdx") Long boardIdx);
    Optional<Comment> findByMember_Id(String memberId);
}


