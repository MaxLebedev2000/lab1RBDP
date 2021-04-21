package com.example.springboot.Controller;

import com.example.springboot.DTO.ArticleDTO;
import com.example.springboot.Entity.Article;
import com.example.springboot.Repository.UserRepository;
import com.example.springboot.Service.ArticleService;
import com.example.springboot.Service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/articles")
@AllArgsConstructor
@Api(tags = {"articles"}, description = "Управление статьями")
public class ArticleController {
    private final ArticleService articleService;
    private final UserService userService;

    @GetMapping("{id}")
    @ApiOperation("Найти статью по id")
    public Article getArticle (@PathVariable long id){
        return articleService.getArticleById(id);
    }

    @GetMapping
    @ApiOperation("Получить все статьи")
    public Iterable<Article> getAllPosts() {
        return articleService.getAllArticles();
    }

    @GetMapping("{name}/{text}/{login}}")
    @ApiOperation("Создать новую статью")
    public Article newArticle(@PathVariable String name, @PathVariable String text, @PathVariable String login) {
        long author = userService.getByLogin(login).getId();
        ArticleDTO articleDTO = new ArticleDTO(name, text, (int) author, 5, false);
        return articleService.newArticle(articleDTO);
    }
}