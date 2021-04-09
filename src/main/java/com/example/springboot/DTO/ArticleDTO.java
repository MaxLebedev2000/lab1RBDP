package com.example.springboot.DTO;

import com.example.springboot.Entity.Category;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ArticleDTO {
    public String name;
    public String text;
    public int author;
    public Category category;
    public boolean status;
}
