    package com.example.login.dto;


    import lombok.Getter;
    import lombok.NoArgsConstructor;
    import lombok.Setter;

    import java.util.List;

    @NoArgsConstructor
    @Getter
    @Setter
    public class CategoryDTO {

        private Long idx;

        private String name;

        private String path;

        private Long parentIdx;

        private String childName; // 자식 카테고리의 이름

        private List<CategoryDTO> children;



        public CategoryDTO(Long idx, String name,List<CategoryDTO> children){
            this.idx = idx;
            this.name = name;
            this.children = children;
            this.path = path;
        }




    /*    public static CategoryDTO toEntity(Category category){
            return new CategoryDTO(
                    category.getIdx(),
                    category.getName(),
                    category.getChildren().stream().map(CategoryDTO::of).collect(Collectors.toList())
            );
        }*/
    }


