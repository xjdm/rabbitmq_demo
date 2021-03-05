package com.idstaa.rabbitmq.demo;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

/**
 * @author chenjie
 * @date 2021/3/5 17:38
 */
@Component
public class MyMessageListener {
    @RabbitListener(queues = "queue.anno")
    public void whenMessageCome(@Payload String messageStr){
        System.out.println(messageStr);
    }
}
