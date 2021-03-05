package com.idstaa.rabbitmq.demo;

import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.net.URI;

/**
 * @author chenjie
 * @date 2021/3/5 17:30
 */
@ComponentScan
@Configuration
@EnableRabbit  // xml也可以使用<rabbit:annotation-driver /> 启用@RabbitListener注解
public class RabbitConfig {

    @Bean
    public ConnectionFactory connectionFactory(){
        return new CachingConnectionFactory(URI.create("amqp://root:123456@192.168.31.204/test"));
    }

    @Bean
    @Autowired
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory){
        return new RabbitAdmin(connectionFactory);
    }

    @Bean
    @Autowired
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory){
        return  new RabbitTemplate(connectionFactory);
    }

    @Bean
    public Queue queue() {
        return QueueBuilder.nonDurable("queue.anno").build();
    }

    @Bean("rabbitListenerContainerFactory")
    @Autowired
    public SimpleRabbitListenerContainerFactory simpleRabbitListenerContainerFactory(ConnectionFactory connectionFactory){
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setAcknowledgeMode(AcknowledgeMode.AUTO);
        /*factory.setConcurrentConsumers(10);
        factory.setMaxConcurrentConsumers(15);*/
        // 按照批次消费消息
        factory.setBatchSize(10);
        return factory;
    }



}
