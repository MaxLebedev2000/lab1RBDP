package com.example.springboot.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDTO {
    public String login;
    public String password;
//    public int rating;
//    public Role role;
}
