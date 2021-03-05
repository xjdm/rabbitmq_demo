package com.idstaa.rabbitmq.demo.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

/**
 * @author chenjie
 * @date 2021/3/5 18:42
 */
@Component
public class MyMessageListener {
    @RabbitListener(queues = "queue.boot")
    public void getMyMessge(@Payload String message, @Header(name ="hello") String value){
        System.out.println(message);
        System.out.println("hello=" + value);
    }
}
