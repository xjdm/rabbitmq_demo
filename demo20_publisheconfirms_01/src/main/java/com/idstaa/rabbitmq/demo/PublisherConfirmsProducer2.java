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
 * @date 2021/3/5 19:09
 */
public class PublisherConfirmsProducer2 {
    public static void main(String[] args) throws NoSuchAlgorithmException, KeyManagementException, URISyntaxException, IOException, TimeoutException, InterruptedException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUri("amqp://root:123456@192.168.31.204:5672/test");

        final Connection connection = factory.newConnection();
        final Channel channel = connection.createChannel();


        // 向RabbitMq服务器发送AMQP命令，将当前通道标记为发送方确认通道
        final AMQP.Confirm.SelectOk selectOk = channel.confirmSelect();
        channel.queueDeclare("queue.pc",true,false,false,null);
        channel.exchangeDeclare("ex.pc","direct",true,false,null);

        channel.queueBind("queue.pc","ex.pc","key.pc");

        String message = "hello-";
        // 批处理的大小
        int batchSize = 10;
        // 用于对需要等待确认消息的技术
        int outstandingCnofirms = 0;
        for (int i = 0; i < 103 ; i++) {
            channel.basicPublish("ex.pc","key.pc",null,(message + i).getBytes());
            outstandingCnofirms ++;
            if (outstandingCnofirms == batchSize){
                // 此时已经有一个批次的消息需要同步等待broker的消息
                // 同步等待
                channel.waitForConfirms(5_000);
                System.out.println("消息已经被确认");
                outstandingCnofirms = 0;
            }

        }

        if(outstandingCnofirms > 0){
            channel.waitForConfirms(5_000);
            System.out.println("剩余消息已经被确认");
        }

        channel.close();
        connection.close();

    }
}
