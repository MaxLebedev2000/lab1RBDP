package com.example.springboot.Repository;

import com.example.springboot.Entity.Category;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CategoryRepository extends CrudRepository<Category, Long> {
    Optional<Category> findByName (String categoryName);
    Optional<Category> findById (long id);
}
