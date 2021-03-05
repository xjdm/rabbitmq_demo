package com.idstaa.rabbitmq.demo;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class ShanghaiErrorConsumer {
    public static void main(String[] args) throws Exception {
        final ConnectionFactory factory = new ConnectionFactory();
        factory.setUri("amqp://root:123456@192.168.31.204:5672/test");
        final Connection connection = factory.newConnection();
        final Channel channel = connection.createChannel();

        // 临时队列，返回值是服务器为该队列生成的名称
        final String queue = channel.queueDeclare().getQueue();
        channel.exchangeDeclare("ex.topic", "topic", true, false, null);
//       beijing.biz-online.error
        channel.queueBind(queue, "ex.topic", "shanghai.*.error");

        channel.basicConsume(queue, (consumerTag, message) -> {
            System.out.println(new String(message.getBody(), "utf-8"));
        }, consumerTag -> {});

    }
}
