package com.example.springboot.Controller;

import com.example.springboot.Entity.Article;
import com.example.springboot.Service.ArticleService;
import com.example.springboot.Service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts")
@AllArgsConstructor

public class ArticleController {
    private final ArticleService articleService;
    private final CategoryService categoryService;

    public Article getArticle (long id){
        return articleService.getArticleById(id);
    }

    @GetMapping
    public Iterable<Article> getAllArticles() {

        @GetMapping("/{id}/comments")
        public Iterable<Article> getCommentsForPost ( @PathVariable int id){
            return commentsService.getPostComments(id);
        }

        @PostMapping("/{id}/comments")
        public Comment addCommentForPost ( @PathVariable int id, @RequestBody CommentDTO commentDTO){
            return commentsService.newCommentForPost(commentDTO, id);
        }
    }
}