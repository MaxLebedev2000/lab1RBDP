package com.example.springboot.controller;

import com.example.springboot.DTO.ArticleDTO;
import com.example.springboot.DTO.MessageDTO;
import com.example.springboot.DTO.NewArticleDTO;
import com.example.springboot.entity.Article;
import com.example.springboot.entity.Role;
import com.example.springboot.entity.Users;
import com.example.springboot.service.ArticleService;
import com.example.springboot.service.CategoryService;
import com.example.springboot.service.MessageService;
import com.example.springboot.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.*;

import javax.jms.JMSException;
import java.util.List;

@RestController
@RequestMapping("/api/articles")
@AllArgsConstructor
@Api(tags = {"articles"}, description = "Управление статьями")
public class ArticleController {
    private final ArticleService articleService;
    private final UserService userService;
    private final MessageService messageService;
    private final CategoryService categoryService;

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

    @PostMapping("/new")
    @ApiOperation("Создать новую статью")
    public Article newArticle(@RequestBody NewArticleDTO newArticleDTO) throws JMSException {
        long author = userService.getByLogin(newArticleDTO.getAuthor()).getId();
        ArticleDTO articleDTO = new ArticleDTO(newArticleDTO.getName(), newArticleDTO.getText(), (int) author, 5, false);
        return articleService.newArticle(articleDTO);
    }

    @GetMapping("/unchecked")
    @ApiOperation("Получить список непроверенных статей")
    public List<Article> getUncheckedArticles() {
        return articleService.getAllUncheckedArticles();
    }

    @GetMapping("/checked")
    @ApiOperation("Получить список всех одобренных статей")
    public List<Article> getCheckedArticles() {
        return articleService.getAllPostedArticles();
    }

    @SneakyThrows
    @GetMapping("/{id}/{mod}/{category}/{rating}")
    @ApiOperation("Одобрить статью")
    public void setStatus(@PathVariable int id, @PathVariable int mod, @PathVariable int category,
                          @PathVariable int rating) {
        Users author = getArticle(id).getAuthor();
        Users moderator = userService.getById(mod);

        if (userService.getById(mod).getRole().equals(Role.MODERATOR)) {
            userService.changeRating(rating, author);
            messageService.newMessage(new MessageDTO("Уважаемый, " + author.getLogin() + "! Ваша статья" +
                    getArticle(id).getName() + "была одобрена модератором.", author.getId()), author.getLogin());
            getArticle(id).setStatus(true);
            getArticle(id).setCategory(categoryService.getById(category));
        } else {
            messageService.newMessage(new MessageDTO("Уважаемый, " + moderator.getLogin() + "! Вы не" +
                    "являетесь модераторм.", mod), moderator.getLogin());
        }
    }

    @GetMapping("/{id}/{mod}")
    @ApiOperation("Удалить статью")
    public void deleteArticle(@PathVariable int id, @PathVariable int mod) {

        Users moderator = userService.getById(mod);
        if (userService.getById(mod).getRole().equals(Role.MODERATOR)) {
            articleService.deleteArticle(id);
            messageService.newMessage(new MessageDTO("Уважаемый, " + moderator.getLogin() + "! Статья" +
                    getArticle(id).getName() + "была успешно удалена .", moderator.getId()), moderator.getLogin());

        } else {
            messageService.newMessage(new MessageDTO("Уважаемый, " + moderator.getLogin() + "! Вы не" +
                    "являетесь модераторм.", mod), moderator.getLogin());
        }
    }

}