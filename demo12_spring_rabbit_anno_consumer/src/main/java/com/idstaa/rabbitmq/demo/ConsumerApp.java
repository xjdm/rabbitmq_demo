package com.idstaa.rabbitmq.demo;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.UnsupportedEncodingException;

/**
 * @author chenjie
 * @date 2021/3/5 16:58
 */
public class ConsumerApp {
    public static void main(String[] args) throws UnsupportedEncodingException {
        // 从指定类加载配置信息
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(RabbitConfig.class);
        // 获取RabbitTemplate 对象
        final RabbitTemplate rabbitTemplate = context.getBean(RabbitTemplate.class);
        // 接收消息
        final Message message = rabbitTemplate.receive("queue.anno");
        // 打印消息
        System.out.println(new String(message.getBody(),message.getMessageProperties().getContentEncoding()));
        // 关闭spring的上下文
        context.close();
    }
}
