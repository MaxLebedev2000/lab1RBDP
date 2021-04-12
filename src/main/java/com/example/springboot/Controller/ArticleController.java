package com.example.springboot.Controller;

import com.example.springboot.DTO.ArticleDTO;
import com.example.springboot.Entity.Article;
import com.example.springboot.Service.ArticleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/posts")
@AllArgsConstructor
@Api(tags = {"posts"}, description = "Управление статьями")
public class ArticleController {
    private final ArticleService articleService;

    public Article getArticle (long id){
        return articleService.getArticleById(id);
    }

    @GetMapping
    @ApiOperation("Получить все статьи")
    public Iterable<Article> getAllPosts() {
        return articleService.getAllArticles();
    }

    @PostMapping("/{id}/text/name")
    @ApiOperation("Создать новую статью")
    public Article newArticle(@RequestBody ArticleDTO articleDTO, Principal principal) {
        return articleService.newArticle(articleDTO, principal.getName());
    }

    @GetMapping("/{id}")
    @ApiOperation("Получить статью по id")
    public Article getArticle(@PathVariable int id) {
        return articleService.getArticleById(id);
    }
}