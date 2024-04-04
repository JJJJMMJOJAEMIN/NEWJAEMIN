package com.example.login.service;

import com.example.login.domain.Board;
import com.example.login.domain.FileEntity;
import com.example.login.dto.FileDTO;
import com.example.login.repository.BoardRepository;
import com.example.login.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class FileService {
    private final FileRepository fileRepository;
    private final BoardRepository boardRepository;

    public void fileWrite(FileDTO dto, MultipartFile file, Long boardId) throws Exception {
        // 프로젝트 경로 내 'file' 디렉토리로 경로 설정
        String projectPath = System.getProperty("user.dir") + "/src/main/resources/static/files";

        // Board 엔티티 찾기
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));

        String originalFileName = file.getOriginalFilename(); // 오리지널 파일 이름 가져오기
        java.io.File saveFile = new java.io.File(projectPath, originalFileName); // 파일 저장 경로 설정
        file.transferTo(saveFile); // 파일 저장

        // FileDTO를 FileEntity로 변환하고 저장합니다.
        FileEntity fileEntity = dto.toEntity(board);
        fileEntity.setFilename(originalFileName); // 파일 이름 설정
        fileEntity.setFilepath("/file/" + originalFileName); // 저장 경로 설정 (여기서 'files' 대신 'file' 사용)
        fileRepository.save(fileEntity); // 파일 정보 저장
    }

    @Transactional
    public void deleteBoardIdx(Long boardIdx) {
        List<FileEntity> files = fileRepository.findByBoard_Idx(boardIdx); // 리스트 <엔티티> 상자 = 리포지토리.리포지토리에 만든 Long board_idx 를 가져옴 Board idx 로 파일 목록 조회
        String projectPath = System.getProperty("user.dir") + "/src/main/resources/static/files"; //파일 경로

        for (FileEntity file : files) {
            String filePath = projectPath + "/" + file.getFilename(); // 파일 경로
            try {
                Files.deleteIfExists(Paths.get(filePath)); // 실제 파일 삭제
            } catch (IOException e) {
                e.printStackTrace(); // 파일 삭제 실패 처리
                // 트랜잭션이 자동으로 롤백됨
            }
            fileRepository.delete(file); // 데이터 베이스에서 파일 정보 삭제
        }
    }

@Transactional
public void modifyFiles(Long boardIdx, List<MultipartFile> file, List<Long> fileIdx) throws Exception {
    // 기존 파일 정보 업데이트
    if (fileIdx != null && !fileIdx.isEmpty()) {
        for (int i = 0; i < fileIdx.size(); i++) {
            Long f_idx = fileIdx.get(i);
            Optional<FileEntity> optionalFileEntity = fileRepository.findById(f_idx);
            if (optionalFileEntity.isPresent()) {
                FileEntity fileEntity = optionalFileEntity.get();

                MultipartFile newFile = file.get(i);
                if (!newFile.isEmpty()) {
                    // 파일 저장 경로 설정
                    String projectPath = System.getProperty("user.dir") + "/src/main/resources/static/files";
                    // 업로드한 파일의 원본 이름 사용
                    String originalFileName = newFile.getOriginalFilename();
                    // 파일 경로 설정 변경
                    String newFilePath = "/files/" + originalFileName;

                    // 기존 파일 삭제
                    String oldFilePath = projectPath + fileEntity.getFilepath();
                    try {
                        Files.deleteIfExists(Paths.get(oldFilePath));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    // 파일 정보 업데이트 (파일 이름과 경로 업데이트 방식 변경)
                    fileEntity.setFilename(originalFileName);
                    fileEntity.setFilepath(newFilePath);
                    fileRepository.save(fileEntity);

                    // 새 파일 저장 위치 변경
                    File newFileSave = new File(projectPath + newFilePath);
                    newFile.transferTo(newFileSave);
                }
            }
        }
    }
}

}
