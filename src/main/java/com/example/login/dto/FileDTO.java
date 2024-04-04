package com.example.login.dto;


import com.example.login.domain.Board;
import com.example.login.domain.FileEntity;
import lombok.*;

@Builder
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class FileDTO {

    private Long idx;

    private Long boardIdx;

    private String filename;

    private String filepath;

    public FileEntity toEntity(Board board){
        return FileEntity.builder()
                .idx(this.idx)
                .board(board)
                .filename(this.filename)
                .filepath(this.filepath)
                .build();
    }


}
