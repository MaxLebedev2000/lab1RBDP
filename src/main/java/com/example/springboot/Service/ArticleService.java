package com.example.springboot.Service;

import com.example.springboot.DTO.ArticleDTO;
import com.example.springboot.DTO.MessageDTO;
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
import java.util.List;

@Service
@AllArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final CategoryRepository categoryRepository;
    private final UserService userService;
    private final MessageService messageService;

    public Iterable<Article> getAllArticles() {
        return articleRepository.findAll();
    }

    public Article getArticleById(long id) {
        return articleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.valueOf(id)));
    }

    public List<Article> getAllPostedArticles() {
        return articleRepository.findByStatusTrue();
    }

    public List<Article> getAllUncheckedArticles() {
        return articleRepository.findByStatusFalse();
    }

    public Article newArticle (ArticleDTO articleDTO) throws ResponseStatusException {
        Users user = userService.getById(articleDTO.author);
        MessageDTO messageDTO;

        if (!isCorrect(articleDTO.text)) {
            messageDTO = new MessageDTO("Уважаемый, " + user.getLogin() + ". Ваша статья " + articleDTO.name + "содержит запрещенные слова", user.getId());
            messageService.newMessage(messageDTO,user.getLogin());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "В статье присутствуют запрещенные слова");
        }

        Article article = new Article();
        article.setAuthor(user);
        article.setText(articleDTO.text);
        article.setName(articleDTO.name);
        article.setStatus(articleDTO.status);
        Category category = categoryRepository.findById(articleDTO.category).
                orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Категория не найдена"));;

        article.setCategory(category);

        messageDTO = new MessageDTO("Уважаемый, " + user.getLogin() + ".Ваша статья " + articleDTO.name + "прошла проверку. Ожидайте дальнейших действий модераторов", user.getId());
        messageService.newMessage(messageDTO,user.getLogin());

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

    public void deleteArticle(int id) {
        Article article = articleRepository.findById((long) id)
                .orElseThrow(() -> new EntityNotFoundException(String.valueOf(id)));

        deleteArticle(article);
    }

    public void deleteArticle(Article article) {
        articleRepository.delete(article);
    }
}