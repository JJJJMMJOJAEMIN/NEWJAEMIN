package com.example.login.repository;

import com.example.login.domain.Category;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class CategoryRepositoryTest {

    @Autowired
    CategoryRepository categoryRepository;

    @Test
    void test() {

        System.out.println("============================================");

        Category parentCategory = categoryRepository.findById(48L).get();
        //Category parentCategory = categoryRepository.findParentAndChildByParentId(48L).get();
        System.out.println("parentCategory.getName() = " + parentCategory.getName());

        System.out.println("//////////////////////////////////////////////");

        List<Category> children = parentCategory.getChildren();
        for (Category child : children) {
            System.out.println("child.getName() = " + child.getName());
        }

        System.out.println("============================================");


    }

}