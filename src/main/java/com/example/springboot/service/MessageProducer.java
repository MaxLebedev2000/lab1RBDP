package com.example.springboot.service;

import com.example.springboot.DTO.ArticleDTO;
import org.apache.activemq.jms.pool.PooledConnectionFactory;
import org.springframework.stereotype.Service;
import javax.jms.*;
import java.io.Serializable;

@Service
public class MessageProducer {
    private final Queue queue;
    private final PooledConnectionFactory pooledConnectionFactory;

    public MessageProducer(Queue queue, PooledConnectionFactory pooledConnectionFactory) {
        this.queue = queue;
        this.pooledConnectionFactory = pooledConnectionFactory;
    }

    public void produceMessage (ArticleDTO articleDTO) throws JMSException{
        final Connection producerConnection = pooledConnectionFactory.createConnection();
        producerConnection.start();

        final Session producerSession = producerConnection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        final javax.jms.MessageProducer producer = producerSession.createProducer(queue);
        ObjectMessage objectMessage = producerSession.createObjectMessage((Serializable) articleDTO);

        producer.send(objectMessage);
        System.out.println("Message is sent to queue.");

        producer.close();
        producerSession.close();
        producerConnection.close();
    }
}