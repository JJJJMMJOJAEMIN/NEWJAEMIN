package com.example.login.repository;

import com.example.login.api.board.BoardByCategoryDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BoardRepositoryTest {
    
    @Autowired BoardRepository boardRepository;
    
    @Test
    void test() {

        List<BoardByCategoryDto> boards = boardRepository.findBoardsByCategoryId(48L);
        System.out.println("boards.size() = " + boards.size());
        for (BoardByCategoryDto board : boards) {
            System.out.println("--");
            System.out.println("board = " + board);
            System.out.println("--");
        }

    }

}