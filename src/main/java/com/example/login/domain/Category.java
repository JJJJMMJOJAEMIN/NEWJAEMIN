package com.example.login.domain;

import com.example.login.dto.CategoryDTO;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Table(name="a_category")
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Setter
@Getter
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "A_C_seq")
    @SequenceGenerator(name = "A_C_seq", sequenceName = "A_C_seq", allocationSize = 1)
    private Long idx;

    @Column(name = "CATEGORYNAME")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_idx")
    private Category parent;

    private String path;

    @OneToMany(mappedBy = "parent")
    private List<Category> children = new ArrayList<>();

    @OneToMany(mappedBy = "category")
    private List<Board> boards = new ArrayList<>();


    public Category(String name) {
        this.name = name;
    }

    public static Category addCategory(CategoryDTO dto) {
        return new Category(dto.getName());
    }
}
/*

           idx   parent_idx    name    path
            1        0         상의     0
            2        1         맨투맨   1-2
            3        2         기모     1-2-3
            4        2         노기모   1-2-4

 */


