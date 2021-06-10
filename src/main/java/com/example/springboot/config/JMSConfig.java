package com.example.springboot.config;

import com.example.springboot.service.MessageConsumer;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.jms.listener.MessageListenerContainer;
import org.apache.activemq.jms.pool.PooledConnectionFactory;
import javax.jms.Queue;
import java.util.Arrays;

@Configuration
@EnableJms
public class JMSConfig {
    final MessageConsumer consumer;

    public JMSConfig(MessageConsumer consumer) {
        this.consumer = consumer;
    }

    @Bean
    public ActiveMQConnectionFactory receiverActiveMQConnectionFactory() {
        ActiveMQConnectionFactory activeMQConnectionFactory =
                new ActiveMQConnectionFactory();
        activeMQConnectionFactory.setTrustAllPackages(true);
        return activeMQConnectionFactory;
    }

    @Bean
    public Queue queue() {
        return new ActiveMQQueue("email-queue");
    }

    @Bean
    public MessageListenerContainer messageListenerContainer(ActiveMQConnectionFactory receiverActiveMQConnectionFactory) {
        DefaultMessageListenerContainer container = new DefaultMessageListenerContainer();
        container.setConnectionFactory(receiverActiveMQConnectionFactory);
        container.setDestinationName("email-queue");
        container.setMessageListener(consumer);
        return container;
    }

    @Bean
    public PooledConnectionFactory pooledConnectionFactory(ActiveMQConnectionFactory receiverActiveMQConnectionFactory) {
        final PooledConnectionFactory pooledConnectionFactory = new PooledConnectionFactory();
        pooledConnectionFactory.setConnectionFactory(receiverActiveMQConnectionFactory);
        return pooledConnectionFactory;
    }
}