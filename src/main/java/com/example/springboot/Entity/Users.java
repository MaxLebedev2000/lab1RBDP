package com.example.springboot.Entity;

import lombok.Builder;
import lombok.Data;
import javax.persistence.*;

@Data
@Entity
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false)
    private boolean isModerator;
    @Column(unique = true, nullable = false)
    private String login;
    @Column(nullable = false)
    private String password;
    private int rating = 0;
}