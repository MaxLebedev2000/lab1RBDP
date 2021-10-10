package com.example.springboot.controller;

import com.example.springboot.DTO.CategoryDTO;
import com.example.springboot.entity.Category;
import com.example.springboot.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@AllArgsConstructor
@Api(tags = {"categories"}, description = "Управление категориями")
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping()
    @ApiOperation("Создать новую категорию")
    public Category newCategory (CategoryDTO categoryDTO) {
        return categoryService.newCategory(categoryDTO);
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Удалить категорию")
    public void deleteCategory(@PathVariable int id) {
        categoryService.deleteCategory(id);
    }

    @GetMapping("/all")
    @ApiOperation("Получить все категории")
    public List<Category> getAllCategories() {
        return categoryService.getCategories();
    }
}
