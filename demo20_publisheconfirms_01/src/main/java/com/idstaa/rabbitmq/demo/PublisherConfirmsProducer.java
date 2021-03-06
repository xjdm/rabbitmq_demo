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
public class PublisherConfirmsProducer {
    public static void main(String[] args) throws NoSuchAlgorithmException, KeyManagementException, URISyntaxException, IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUri("amqp://root:123456@192.168.31.204:5672/test");

        final Connection connection = factory.newConnection();
        final Channel channel = connection.createChannel();


        // 向RabbitMq服务器发送AMQP命令，将当前通道标记为发送方确认通道
        final AMQP.Confirm.SelectOk selectOk = channel.confirmSelect();
        channel.queueDeclare("queue.pc",true,false,false,null);
        channel.exchangeDeclare("ex.pc","direct",true,false,null);

        channel.queueBind("queue.pc","ex.pc","key.pc");
        // 发送消息
        channel.basicPublish("ex.pc","key.pc",null,"hello world".getBytes());

        // 同步方式等待RabbitMQ的确认消息
        try{
            channel.waitForConfirmsOrDie(5_000);
            System.out.println("发送的消息已经得到确认");
        } catch (IllegalStateException ex) {
            System.out.println("发送消息的通道不是PublisherConfirms通道");
        } catch (IOException ex){
            System.out.println("消息被拒收");
        } catch (TimeoutException ex){
            System.out.println("等待消息确认超时");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        channel.close();
        connection.close();

    }
}