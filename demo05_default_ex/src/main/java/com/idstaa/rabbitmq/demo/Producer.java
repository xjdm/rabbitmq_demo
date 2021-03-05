package com.idstaa.rabbitmq.demo;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Producer {
    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
/*        factory.setHost("192.168.31.204");
        factory.setVirtualHost("/test");
        factory.setUsername("root");
        factory.setPassword("123456");
        factory.setPort(5672);*/
        factory.setUri("amqp://root:123456@192.168.31.204:5672/test");

        final Connection connection = factory.newConnection();
        final Channel channel = connection.createChannel();

        channel.queueDeclare("queue.default.ex",  // 队列的名字
                false,  // 能否活到MQ重启
                false,   // 是否只能你自己的连接来使用
                false,  // 是否自动删除
                null); // 有没有属性

        // 在发送消息的时候没有指定交换器的名字，此时使用的是默认的交换器，默认交换器就没有名字
        // 路由键就是目的地消息队列的名字
        channel.basicPublish("", "queue.default.ex", null, "hello lagou".getBytes());

        channel.close();
        connection.close();

    }
}
