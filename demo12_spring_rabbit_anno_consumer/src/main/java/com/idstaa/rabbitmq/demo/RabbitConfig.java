package com.idstaa.rabbitmq.demo;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.URI;

/**
 * @author chenjie
 * @date 2021/3/5 16:58
 */
@Configuration
public class RabbitConfig {
    @Bean
    public ConnectionFactory connectionFactory(){
        return new CachingConnectionFactory(URI.create("amqp://root:123456@192.168.31.204:5672/test"));
    }

    @Bean
    @Autowired
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory){
        return  new RabbitTemplate(connectionFactory);
    }

    @Bean
    @Autowired
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory){
        return new RabbitAdmin(connectionFactory);
    }

    @Bean
    @Autowired
    public org.springframework.amqp.core.Queue queue(){
        Queue queue = QueueBuilder.nonDurable("queue.anno").build();
        return queue;
    }
}
