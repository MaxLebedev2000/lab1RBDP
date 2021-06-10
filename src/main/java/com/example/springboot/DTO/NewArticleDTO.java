package com.example.springboot.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NewArticleDTO {
    public String name;
    public String text;
    public String author;
}