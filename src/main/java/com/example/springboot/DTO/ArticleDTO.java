package com.example.springboot.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class ArticleDTO implements Serializable {
    public String name;
    public String text;
    public int author;
    public int category;
    public boolean status;
}