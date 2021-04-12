package com.example.springboot.Controller;

import com.example.springboot.Service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/comments")
@AllArgsConstructor
@Api(tags = {"comments"}, description = "Управление категориями")
public class CategoryController {
    private final CategoryService categoryService;

    @DeleteMapping("/{id}")
    @ApiOperation("Удалить категорию")
    public void deleteCategory(@PathVariable int id) {
        categoryService.deleteCategory(id);
    }
}
