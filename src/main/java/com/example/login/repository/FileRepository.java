package com.example.login.repository;

import com.example.login.domain.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FileRepository extends JpaRepository<FileEntity,Long> {
    List<FileEntity> findByBoard_Idx(Long boardIdx);
    List<FileEntity> findByIdxIn(List<Long> idx);
}