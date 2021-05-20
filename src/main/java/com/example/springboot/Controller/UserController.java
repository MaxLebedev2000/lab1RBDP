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

    @DeleteMapping("/{id}")
    @ApiOperation("Удалить пользователя")
    public void deleteUser(@PathVariable int id) {
        userService.deleteUser(id);
    }

    @GetMapping("/{login}")
    @ApiOperation("Получить пользователя по логину или id")
    public Users getUserByLoginOrId(@PathVariable String login) {
        try {
            return userService.getById(Integer.parseInt(login));
        } catch (NumberFormatException e) {
            return userService.getByLogin(login);
        }
    }

    @GetMapping()
    @ApiOperation("Получить всех пользователей")
    public List<Users> getAllUsers() {
        return userService.getUsers();
    }

    @PostMapping("/new")
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