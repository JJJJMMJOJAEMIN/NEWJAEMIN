package com.example.login.api.board;


import com.example.login.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class BoardApiController {

    private final BoardRepository boardRepository;

    @GetMapping("/category/{categoryId}")
    public List<BoardByCategoryDto> findByCategoryId(@PathVariable(value = "categoryId") Long categoryId) {
        return boardRepository.findBoardsByCategoryId(categoryId);
    }
}
