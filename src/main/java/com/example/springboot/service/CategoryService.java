package com.example.springboot.service;

import com.example.springboot.DTO.CategoryDTO;
import com.example.springboot.entity.Category;
import com.example.springboot.entity.Message;
import com.example.springboot.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityNotFoundException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import java.util.List;

@Service
@AllArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final UserTransaction userTransaction;

    @PreAuthorize("hasAuthority('users:write')")
    public Category getById(int id) {
        return categoryRepository.findById((long) id)
                .orElseThrow(() -> new EntityNotFoundException(String.valueOf(id)));
    }

    @PreAuthorize("hasAuthority('users:write')")
    public Category getByName (String name) {
        return categoryRepository.findByName(name)
                .orElseThrow(() -> new EntityNotFoundException(String.valueOf(name)));
    }

    @PreAuthorize("hasAuthority('users:write')")
    public Category newCategory (CategoryDTO categoryDTO) {
        try {
            userTransaction.begin();
            Category category = new Category();
            category.setName(categoryDTO.name);
            category = categoryRepository.save(category);
            userTransaction.commit();
            return category;

        } catch (Exception e) {
            try {
                userTransaction.rollback();
            } catch (SystemException systemException) {
                systemException.printStackTrace();
            }
            throw new ResponseStatusException(HttpStatus.I_AM_A_TEAPOT, "При выполнении транзакции возникла ошибка");
        }

    }

    @PreAuthorize("hasAuthority('users:write')")
    public void deleteCategory (int id) {
        try {
            userTransaction.begin();
            Message message = new Message();
            Category category = categoryRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException(String.valueOf(id)));

            deleteCategory(category);
            userTransaction.commit();
        } catch (Exception e) {
            try {
                userTransaction.rollback();
            } catch (SystemException systemException) {
                systemException.printStackTrace();
            }
            throw new ResponseStatusException(HttpStatus.I_AM_A_TEAPOT, "При выполнении транзакции возникла ошибка");
        }

    }

    public void deleteCategory(Category category) {
        categoryRepository.delete(category);
    }

    public List<Category> getCategories() {
        return categoryRepository.findAllCategories();
    }
}