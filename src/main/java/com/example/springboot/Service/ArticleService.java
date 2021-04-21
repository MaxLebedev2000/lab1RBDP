package com.example.springboot.Service;

import com.example.springboot.DTO.ArticleDTO;
import com.example.springboot.Entity.Article;
import com.example.springboot.Entity.Category;
import com.example.springboot.Entity.Users;
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

    public Article newArticle (ArticleDTO articleDTO) throws ResponseStatusException {
        Users user = userService.getById(articleDTO.author);

        if (!isCorrect(articleDTO.text)) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "В статье присутствуют запрещенные слова");

        Article article = new Article();
        article.setAuthor(user);
        article.setText(articleDTO.text);
        article.setName(articleDTO.name);
        article.setStatus(articleDTO.status);
        Category category = categoryRepository.findById(articleDTO.category).
                orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Категория не найдена"));;

        article.setCategory(category);
        return articleRepository.save(article);
    }

    public boolean isCorrect(String text) {
        int count = -1;
        String[] words = {"GunDone", "ChopIsDish", "YourBunnyWrote", "PeaceDeath"};
        for (String word : words) {
            int indexWord = text.indexOf(word);
            if (indexWord != -1) count++;
        }
        return count == -1;
    }
}