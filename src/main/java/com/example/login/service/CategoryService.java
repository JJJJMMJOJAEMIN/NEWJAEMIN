package com.example.login.service;

import com.example.login.domain.Category;
import com.example.login.dto.CategoryDTO;
import com.example.login.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@RequiredArgsConstructor
@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<Category> categoryList(){

        return categoryRepository.findAll();
    }


    @Transactional(readOnly = true)
    public Category CategoryView(Long idx){
        return categoryRepository.findById(idx).get();
    }




    public Category addOrUpdateCategory(Long parentIdx, CategoryDTO dto) {
        Category parentCategory = categoryRepository.findById(parentIdx)
                .orElseThrow(() -> new IllegalStateException(parentIdx + "에 해당하는 부모카테고리가 없습니다."));

        Category category = new Category();
        category.setName(dto.getChildName());
        category.setParent(parentCategory);

        Category newCategory = categoryRepository.save(category);

        String path;
        if (parentCategory.getPath() == null || parentCategory.getPath().isEmpty()) {
            path = parentIdx + "-" + newCategory.getIdx();
        } else {
            path = parentCategory.getPath() + "-" + newCategory.getIdx();
        }

        newCategory.setPath(path);

        return categoryRepository.save(newCategory);
    }



}

