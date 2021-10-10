package com.example.springboot.repository;

import com.example.springboot.entity.Category;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends CrudRepository<Category, Long> {
    Optional<Category> findByName (String categoryName);
    Optional<Category> findById (long id);

    @Query("select c from Category c")
    List<Category> findAllCategories();
}
