package com.idstaa.rabbitmq.demo;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeoutException;

/**
 * 推模式
 * @author chenjie
 * @date 2021/2/20 12:20
 */
public class HelloConsumeConsumer {
    public static void main(String[] args) throws IOException, TimeoutException,
            NoSuchAlgorithmException, KeyManagementException, URISyntaxException {
        // 获取连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        // 指定协议，用户名，密码，host，端口号，虚拟主机
        factory.setUri("amqp://root:123456@192.168.31.204:5672/test");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        // 确保MQ中有该队列，如果没有则创建
        channel.queueDeclare("queue.biz", false, false, true, null);
        // 监听消息，一旦有消息过来，就调用第一个lambda表达式
        channel.basicConsume("queue.biz",((consumerTag, message) -> {
            System.out.println(new String(message.getBody()));
        }),(consumerTag -> {}));

       // channel.close();
        // connection.close();
    }
}
