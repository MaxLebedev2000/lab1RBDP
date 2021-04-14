package com.example.springboot.Controller;

import com.example.springboot.DTO.UserDTO;
import com.example.springboot.Entity.Users;
import com.example.springboot.Service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
@Api(tags = {"users"}, description = "Управление пользователями")
public class UserController {
    private final UserService userService;

    @GetMapping("/{id}")
    @ApiOperation("Получить пользователя по id")
    public Users getUserById(@PathVariable int id) {
        return userService.getById(id);
    }

    @PostMapping
    @ApiOperation("Создать пользователя")
    public Users newUser(@RequestBody UserDTO userDTO) {
        return userService.newUser(userDTO);
    }

    @GetMapping("/moderators")
    @ApiOperation("Получить список модераторов")
    public List<Users> getModerators() {
        return userService.getModerators();
    }

    @PostMapping("/moderators")
    @ApiOperation("Создать модератора")
    public void addModerator(@RequestBody String login) {
        userService.addModerator(login);
    }
}
