package com.example.springboot.mappers;

import com.example.springboot.DTO.ArticleDTO;
import com.example.springboot.entity.Article;
import com.example.springboot.entity.Users;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class ArticleMapper {

    //This method map users dto to entity
    public Article dtoToEntity(ArticleDTO articleDTO, Users user) {
        Article article = new Article();

        article.setAuthor(user);
        article.setText(articleDTO.text);
        article.setName(articleDTO.name);
        article.setStatus(articleDTO.status);

        return article;
    }
}
