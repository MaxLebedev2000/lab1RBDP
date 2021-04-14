package com.example.springboot.Entity;

import lombok.Data;
import javax.persistence.*;

@Data
@Entity
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private boolean isModerator;
    private String login;
    private String password;
    private int rating;
}