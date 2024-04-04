package com.example.login.domain;


import jakarta.persistence.*;
import lombok.*;


@Setter
@Table(name="A_FILE")
@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FileEntity {

        @Id
        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "A_F_seq")
        @SequenceGenerator(name = "A_F_seq", sequenceName = "A_F_seq", allocationSize = 1)
        private Long idx; //번호

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name="board_idx", referencedColumnName = "idx")
        private Board board;

        private String filename;//파일의 이름

        private String filepath;//파일의 경로

        public String getBoardIdx() {
                return this.getBoard().getIdx().toString();
        }
    }
