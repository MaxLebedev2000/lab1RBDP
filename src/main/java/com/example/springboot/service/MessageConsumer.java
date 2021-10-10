package com.example.springboot.service;

import com.example.springboot.DTO.ArticleDTO;
import com.example.springboot.DTO.MessageDTO;
import com.example.springboot.entity.Users;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import java.util.List;

@Service
public class MessageConsumer  {
    private final MessageService messageService;
    private final UserService userService;

    public MessageConsumer(MessageService messageService, UserService userService) {
        this.messageService = messageService;
        this.userService = userService;
    }

    @JmsListener(destination = "email-queue")
    public void onMessage(Message message) {
        System.out.println("Consumer started to process new message");
        try {
            ArticleDTO articleDTO = (ArticleDTO) ((ObjectMessage) message).getObject();
            List<Users> users = userService.getModerators();
            for (Users u : users) {
                MessageDTO messageDTO = new MessageDTO("Статья " + articleDTO.name +
                        " ожидает проверки, распределите между собой, кто её будет проверять", u.getId());
                messageService.newMessage(messageDTO, u.getLogin());
                Thread.sleep(10 * 1000);
            }
        } catch (JMSException | InterruptedException jmsException) {
            jmsException.printStackTrace();
        }


        System.out.println("Consumer finished to process new message");
    }
}