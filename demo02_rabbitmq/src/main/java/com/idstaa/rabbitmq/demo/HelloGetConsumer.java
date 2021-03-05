package com.idstaa.rabbitmq.demo;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.GetResponse;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeoutException;

/**
 * 拉模式
 * @author chenjie
 * @date 2021/2/20 12:20
 */
public class HelloGetConsumer {
    public static void main(String[] args) throws IOException, TimeoutException,
            NoSuchAlgorithmException, KeyManagementException, URISyntaxException {
        // 获取连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        // 指定协议，用户名，密码，host，端口号，虚拟主机
        factory.setUri("amqp://root:123456@192.168.31.204:5672/test");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        GetResponse getResponse = channel.basicGet("queue.biz", true);
        byte[] body = getResponse.getBody();
        System.out.println(new String(body));
        channel.close();
        connection.close();
    }
}
