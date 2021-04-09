package com.example.springboot.Repository;

import com.example.springboot.Entity.Article;
import org.springframework.data.repository.CrudRepository;
import java.util.Optional;

public interface ArticleRepository extends CrudRepository<Article,Long> {
    Optional<Article> findByName (String name);
}