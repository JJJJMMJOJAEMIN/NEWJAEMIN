package com.example.login.api.board;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class BoardByCategoryDto {
    private Long id;
    private String userid;
    private String title;
    private String username;
    private LocalDateTime postedDate;

}
