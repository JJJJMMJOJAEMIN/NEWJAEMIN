package com.example.login.service;


import com.example.login.domain.Board;
import com.example.login.dto.BoardDTO;
import com.example.login.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
@RequiredArgsConstructor
@Service
public class BoardService {

    private final BoardRepository boardRepository;
    private final FileService fileService;
    //작성
    public void write(Board board) {
        boardRepository.save(board);
    }


    //리스트&페이지
    public Page<Board> BoardList(Pageable pageable) {

        return boardRepository.findAll(pageable);
    }

    //검색 쿼리문 불러와서 대충 처리하는거 같은데 맞을듯
    public Page<Board> boardSearchList(String searchKeyword, String searchType, Pageable pageable) {
        return boardRepository.search(searchKeyword, searchType, pageable);
    }

    @Transactional(readOnly = true)
    public Board BoardView(Long idx) {
        return boardRepository.findById(idx).get();
    }

    public void BoardDelete(Long idx) {
        fileService.deleteBoardIdx(idx);
        boardRepository.deleteById(idx);
    }

    @Transactional
    public void boardUpdate(Long idx, BoardDTO dto, List<MultipartFile> file,List<Long> fileIdx) throws Exception {
        Optional<Board> optionalBoard = boardRepository.findById(idx);
        if (optionalBoard.isPresent()) {
            Board board = optionalBoard.get();
            board.setTitle(dto.getTitle());
            board.setContent(dto.getContent());

            // 파일 처리를 위해 modifyFiles 호출, 적절한 newFiles 리스트를 전달해야 함
            if(fileIdx != null && !fileIdx.isEmpty()) {
                fileService.modifyFiles(idx, file, fileIdx);
            }
            boardRepository.save(board);
        }
    }
}

