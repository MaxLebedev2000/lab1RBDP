package com.example.springboot.Service;

import com.example.springboot.DTO.MessageDTO;
import com.example.springboot.Entity.Message;
import com.example.springboot.Entity.Users;
import com.example.springboot.Repository.MessageRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityNotFoundException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import java.util.List;

@Service
@AllArgsConstructor
public class MessageService {
    private final MessageRepository messageRepository;
    private final UserService userService;
    private final UserTransaction userTransaction;
    @PreAuthorize("hasAuthority('users:read')")
    public Message getById(int id) {
        return messageRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.valueOf(id)));
    }

    @PreAuthorize("hasAuthority('users:read')")
    public List<Message> getByLogin (String login) {
        Users user = userService.getByLogin(login);
        return messageRepository.findByLogin(user);
    }
    @PreAuthorize("hasAuthority('users:read')")
    public List<Message> getMessages () {
        return messageRepository.findAllMessages();
    }

    @PreAuthorize("hasAuthority('users:write')")
    public Message newMessage (MessageDTO messageDTO, String login) {

        try {
            userTransaction.begin();
            Message message = new Message();
            message.setText(messageDTO.getText());
            message.setUser(userService.getByLogin(login));
            message = messageRepository.save(message);
            userTransaction.commit();
            return message;
        } catch (Exception e) {
            try {
                userTransaction.rollback();
            } catch (SystemException systemException) {
                systemException.printStackTrace();
            }
            throw new ResponseStatusException(HttpStatus.I_AM_A_TEAPOT, "При выполнении транзакции возникла ошибка");
        }


    }
    @PreAuthorize("hasAuthority('users:write')")
    public void deleteMessage (int id) {

        try {
            userTransaction.begin();
            Message message = messageRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException(String.valueOf(id)));

            deleteMessage(message);
            userTransaction.commit();
        } catch (Exception e) {
            try {
                userTransaction.rollback();
            } catch (SystemException systemException) {
                systemException.printStackTrace();
            }
            throw new ResponseStatusException(HttpStatus.I_AM_A_TEAPOT, "При выполнении транзакции возникла ошибка");
        }
    }

    public void deleteMessage(Message message) {
        messageRepository.delete(message);
    }
}
