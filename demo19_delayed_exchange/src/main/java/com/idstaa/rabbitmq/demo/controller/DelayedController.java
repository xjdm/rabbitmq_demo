package com.idstaa.rabbitmq.demo.controller;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.core.MessagePropertiesBuilder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;

/**
 * @author chenjie
 * @date 2021/3/6 13:58
 */
@RestController
public class DelayedController {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @RequestMapping("/meeting/{second}")
    public String bootMeeting (@PathVariable Integer second) throws UnsupportedEncodingException {

        final MessageProperties messageProperties = MessagePropertiesBuilder.newInstance()
                // 设置消息的过期时间
                .setHeader("x-delay",(second -10)*1000)
                .setContentEncoding("utf-8")
                .build();

        final Message message = MessageBuilder
                .withBody("还有10s开会了".getBytes("utf-8"))
                .andProperties(messageProperties)
                .build();

        rabbitTemplate.send("ex.delayed","key.delayed",message);
        return "会议好了";
    }

}
