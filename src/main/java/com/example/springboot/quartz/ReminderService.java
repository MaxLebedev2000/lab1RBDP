package com.example.springboot.quartz;

import com.example.springboot.DTO.MessageDTO;
import com.example.springboot.entity.Article;
import com.example.springboot.entity.Users;
import com.example.springboot.service.ArticleService;
import com.example.springboot.service.MessageService;
import com.example.springboot.service.UserService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ReminderService {
    final ArticleService articleService;
    UserService userService;
    MessageService messageService;

    public ReminderService (ArticleService articleService) {
        this.articleService = articleService;
    }

    public void setRemindsToModerators () {
        List<Article> articles = articleService.getAllUncheckedArticles();
        for (Article a : articles) {
            List<Users> users = userService.getModerators();
            for (Users u : users) {
                MessageDTO  messageDTO = new MessageDTO("Статья "  + a.getName() +
                        " ожидает проверки, распределите между собой, кто её будет проверять", u.getId());
               messageService.newMessage (messageDTO, u.getLogin());
            }
        }

        List<Article> checkedArticles = articleService.getAllPostedArticles();

        for (Article a : checkedArticles) {
            List<Users> users = userService.getModerators();
            for (Users u : users) {
                messageService.deleteMessageToModerator("Статья "  + a.getName() +
                        " ожидает проверки, распределите между собой, кто её будет проверять");
            }
        }
    }
}
