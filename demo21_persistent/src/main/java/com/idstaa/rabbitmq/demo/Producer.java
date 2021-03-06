package com.idstaa.rabbitmq.demo;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeoutException;

/**
 * @author chenjie
 * @date 2021/3/5 22:24
 */
public class Producer {
    public static void main(String[] args) throws NoSuchAlgorithmException, KeyManagementException, URISyntaxException, IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUri("amqp://root:123456@192.168.31.204/test");
        final Connection connection = factory.newConnection();
        final Channel channel = connection.createChannel();

        channel.queueDeclare("queue.persistent", true, false, false, null);
        channel.exchangeDeclare("ex.persistent", "direct", true, false, false, null);
        channel.queueBind("queue.persistent", "ex.persistent", "key.persistent");

        new AMQP.BasicProperties()
                .builder()
                .deliveryMode(2)
                .build();
        channel.basicPublish("ex.persistent",
                "key.persistent",
                null,
                "hello world".getBytes());

        channel.close();
        connection.close();


    }
}
