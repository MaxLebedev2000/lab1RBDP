package com.example.springboot.Entity;

import lombok.Data;
import javax.persistence.*;

@Data
@Entity
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String text;
    private String name;
    private boolean status;

    @ManyToOne
    private Users author;
    @ManyToOne
    private Category category;
}
