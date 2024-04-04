package com.example.login.repository;


import com.example.login.api.board.BoardByCategoryDto;
import com.example.login.domain.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
    @Query("SELECT b FROM Board b WHERE " +
            "(:searchType is null OR :searchType = '' OR " +
            "(:searchType = 'all' AND (b.member.id LIKE %:searchKeyword% OR b.title LIKE %:searchKeyword% OR b.name LIKE %:searchKeyword%)) OR " +
            "(:searchType = 'memberId' AND b.member.id LIKE %:searchKeyword%) OR " +
            "(:searchType = 'title' AND b.title LIKE %:searchKeyword%) OR " +
            "(:searchType = 'name' AND b.name LIKE %:searchKeyword%))")
    Page<Board> search(@Param("searchKeyword") String searchKeyword, @Param("searchType") String searchType, Pageable pageable);

    @Query("select new com.example.login.api.board.BoardByCategoryDto(b.idx, m.id, b.title, m.name, b.regdate) " +
            "from Board b " +
            "join b.member m " +
            "join b.category c " +
            "where c.idx = :categoryId")
    List<BoardByCategoryDto> findBoardsByCategoryId(@Param("categoryId") Long categoryId);
}


