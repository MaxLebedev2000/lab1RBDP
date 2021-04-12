package com.example.springboot.Service;

import com.example.springboot.DTO.ArticleDTO;
import com.example.springboot.Entity.Article;
import com.example.springboot.Entity.Category;
import com.example.springboot.Entity.User;
import com.example.springboot.Repository.ArticleRepository;
import com.example.springboot.Repository.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityNotFoundException;

@Service
@AllArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final CategoryRepository categoryRepository;
    private final UserService userService;

    public Iterable<Article> getAllArticles() {
        return articleRepository.findAll();
    }

    public Article getArticleById(long id) {
        return articleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.valueOf(id)));
    }

    public Article newArticle (ArticleDTO articleDTO, String login) {
        User user = userService.getByLogin(login);
        Article article = new Article();
        article.setAuthor(user);
        article.setText(articleDTO.text);
        article.setName(articleDTO.name);
        article.setStatus(articleDTO.status);
        Category category = categoryRepository.findByName(articleDTO.category).
                orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Категория не найдена"));;

        article.setCategory(category);
        return articleRepository.save(article);
    }
}