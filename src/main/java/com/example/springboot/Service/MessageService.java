package com.example.springboot.Service;
import com.example.springboot.DTO.MessageDTO;
import com.example.springboot.Entity.Message;
import com.example.springboot.Entity.Users;
import com.example.springboot.Repository.MessageRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@AllArgsConstructor
public class MessageService {
    private final MessageRepository messageRepository;
    private final UserService userService;

    public Message getById(int id) {
        return messageRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.valueOf(id)));
    }

    public List<Message> getByLogin (String login) {
        Users user = userService.getByLogin(login);
        return messageRepository.findByLogin(user);
    }

    public List<Message> getMessages () {
        return messageRepository.findAllMessages();
    }

    public Message newMessage (MessageDTO messageDTO, String login) {
        Message message = new Message();
        message.setText(messageDTO.getText());
        message.setUser(userService.getByLogin(login));

        return messageRepository.save(message);
    }

    public void deleteMessage (int id) {
        Message message = messageRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.valueOf(id)));

        deleteMessage(message);
    }

    public void deleteMessage(Message message) {
        messageRepository.delete(message);
    }
}
