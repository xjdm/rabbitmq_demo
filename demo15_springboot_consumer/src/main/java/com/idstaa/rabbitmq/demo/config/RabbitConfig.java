package com.idstaa.rabbitmq.demo.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author chenjie
 * @date 2021/3/5 18:41
 */
@Configuration
public class RabbitConfig {
    @Bean
    public Queue queue(){
        return QueueBuilder.nonDurable("queue.boot").build();
    }
}
