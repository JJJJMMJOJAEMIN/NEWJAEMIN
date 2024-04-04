package com.example.login.controller;

import com.example.login.domain.Category;
import com.example.login.dto.CategoryDTO;
import com.example.login.repository.CategoryRepository;
import com.example.login.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;


@Log4j2
@Controller
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;
    private final CategoryRepository categoryRepository;


    @GetMapping("/category/write")
    public String categoryForm(){
        return "categoryForm";
    }

    @PostMapping("/category/writepro")
    public String categoryPro(CategoryDTO dto){

        Category category = Category.addCategory(dto);

        categoryRepository.save(category);

        return "redirect:/category/list";
    }

    @GetMapping("/category/list")
    public String categoryList(Model model){

        List<Category> list = categoryService.categoryList();

        model.addAttribute("list", list);

        return "categorylist";
    }
    @GetMapping("/category/view/{idx}")
    public String categoryView(Model model, @PathVariable(value = "idx")Long idx){

        Category category = categoryService.CategoryView(idx);

        model.addAttribute("category", category);

        return "categoryView";
    }

    @GetMapping("/category/update/{idx}")
    public String categoryUpdate( Model model, @PathVariable(value = "idx") Long idx){

        model.addAttribute("category", categoryService.CategoryView(idx));

        return "categoryUpdate";
    }

    @PostMapping("/update/{idx}")
    public String updateCategory(@PathVariable("idx") Long idx, CategoryDTO dto) {

    Category category = categoryService.addOrUpdateCategory(dto.getParentIdx(), dto);


    return "redirect:/category/list";
}


}
