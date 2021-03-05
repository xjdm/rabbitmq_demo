package com.idstaa.rabbitmq.demo;


import org.springframework.amqp.core.*;
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
 * @date 2021/3/5 16:32
 */
@Configuration
public class RabbitConfig {
    // 连接工厂
    @Bean
    public ConnectionFactory connectionFactory(){
        ConnectionFactory factory = new CachingConnectionFactory(URI.create("amqp://root:123456@192.168.31.204:5672/test"));
        return factory;
    }

    // RabbitTemplate
    @Bean
    @Autowired
    public RabbitTemplate rabbitTemplate(ConnectionFactory factory){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(factory);
        return rabbitTemplate;
    }

    // RabbitAdmin
    @Bean
    @Autowired
    public RabbitAdmin rabbitAdmin(ConnectionFactory factory){
        RabbitAdmin rabbitAdmin = new RabbitAdmin(factory);
        return rabbitAdmin;
    }

    // Queue
    @Bean
    public Queue queue(){
        Queue queue = QueueBuilder.nonDurable("queue.anno").build();
        return queue;
    }

    // Exchange
    @Bean
    public Exchange exchange(){
        final FanoutExchange fanoutExchange = new FanoutExchange("ex.anno.fanout", false, false, null);
        return fanoutExchange;
    }

    // Binding
    @Bean
    @Autowired
    public Binding binding(Queue queue,Exchange exchange){
        // 创建一个绑定，不指定绑定的参数
        Binding binding = BindingBuilder.bind(queue).to(exchange).with("key.anno").noargs();
        return binding;
    }
}
