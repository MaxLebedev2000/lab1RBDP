package com.example.springboot.entity;

import lombok.Data;
import javax.persistence.*;

@Data
@Entity
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false)
    private String text;
    @Column(unique = true, nullable = false)
    private String name;
    private boolean status = false;

    @ManyToOne
    private Users author;
    @ManyToOne
    private Category category;
}
