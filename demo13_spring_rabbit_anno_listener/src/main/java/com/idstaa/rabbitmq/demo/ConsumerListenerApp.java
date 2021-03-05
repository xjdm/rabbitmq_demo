package com.idstaa.rabbitmq.demo;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author chenjie
 * @date 2021/3/5 18:01
 */
public class ConsumerListenerApp {
    public static void main(String[] args) {
        new AnnotationConfigApplicationContext(RabbitConfig.class);
    }
}
