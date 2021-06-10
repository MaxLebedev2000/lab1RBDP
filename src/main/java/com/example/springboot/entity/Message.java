package com.example.springboot.entity;

import lombok.Data;
import javax.persistence.*;

@Data
@Entity
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;
    boolean isPosted;
    String text;
    @ManyToOne
    Users user;
}