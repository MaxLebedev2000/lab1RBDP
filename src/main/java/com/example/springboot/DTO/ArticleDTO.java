package com.example.springboot.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ArticleDTO {
    public String name;
    public String text;
    public int author;
    public int category;
    public boolean status;
}