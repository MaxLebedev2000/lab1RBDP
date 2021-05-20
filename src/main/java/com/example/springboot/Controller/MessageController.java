package com.example.springboot.Controller;

import com.example.springboot.DTO.MessageDTO;
import com.example.springboot.Entity.Message;
import com.example.springboot.Service.MessageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/message")
@AllArgsConstructor
@Api(tags = {"messages"}, description = "Управление сообщениями")
public class MessageController {
    private final MessageService messageService;

    @GetMapping("/{id}")
    @ApiOperation("Получить сообщение по id")
    public Message getMessageId(@PathVariable int id) {
        return messageService.getById(id);
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Удалить сообщение")
    public void deleteMessage(@PathVariable int id) {
        messageService.deleteMessage(id);
    }

    @GetMapping()
    @ApiOperation("Получить все сообщения")
    public List<Message> getAllMessages () {
        return messageService.getMessages();
    }

    @GetMapping("/find/{login}")
    @ApiOperation("Получить сообщения по пользователю, которому они были отправлены")
    public List<Message> getUserByLogin(@RequestParam(value = "login") String login) {
        return messageService.getByLogin(login);
    }

    @PostMapping("/create/{login}")
    @ApiOperation("Создать сообщение")
    public Message newMessage(@PathVariable String login, @RequestBody MessageDTO messageDTO) {
        return messageService.newMessage(messageDTO, login);
    }
}