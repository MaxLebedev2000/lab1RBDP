package com.example.springboot.Controller;

import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
@Api(tags = {"users"}, description = "Управление пользователями")
public class UserController {
}
