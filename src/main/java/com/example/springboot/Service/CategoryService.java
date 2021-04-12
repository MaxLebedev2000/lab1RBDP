package com.example.springboot.Service;

import com.example.springboot.DTO.CategoryDTO;
import com.example.springboot.DTO.UserDTO;
import com.example.springboot.Entity.Category;
import com.example.springboot.Entity.User;
import com.example.springboot.Repository.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
@AllArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public Category getById(int id) {
        return categoryRepository.findById((long) id)
                .orElseThrow(() -> new EntityNotFoundException(String.valueOf(id)));
    }

    public Category getByName (String name) {
        return categoryRepository.findByName(name)
                .orElseThrow(() -> new EntityNotFoundException(String.valueOf(name)));
    }

    public Category newCategory (CategoryDTO categoryDTO) {
        Category category = new Category();
        category.setName(categoryDTO.name);

        return categoryRepository.save(category);
    }

    public void deleteCategory (int id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.valueOf(id)));

        deleteCategory(category);
    }

    public void deleteCategory(Category category) {
        categoryRepository.delete(category);
    }
}