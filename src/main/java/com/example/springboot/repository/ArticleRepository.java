package com.example.springboot.repository;

import com.example.springboot.entity.Article;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ArticleRepository extends CrudRepository<Article,Long> {
    Optional<Article> findByName (String name);

    @Query("select a from Article a where a.status = true")
    List<Article> findByStatusTrue();


    @Query("select a from Article a where a.status = false")
    List<Article> findByStatusFalse();

    void updateArticle(Article article);
}