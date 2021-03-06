package com.idstaa.rabbitmq.demo;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.TimeoutException;

/**
 * @author chenjie
 * @date 2021/3/5 19:09
 */
public class PublisherConfirmsProducer3 {
    public static void main(String[] args) throws NoSuchAlgorithmException, KeyManagementException, URISyntaxException, IOException, TimeoutException, InterruptedException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUri("amqp://root:123456@192.168.31.204:5672/test");

        final Connection connection = factory.newConnection();
        final Channel channel = connection.createChannel();


        // 向RabbitMq服务器发送AMQP命令，将当前通道标记为发送方确认通道
        final AMQP.Confirm.SelectOk selectOk = channel.confirmSelect();
        channel.queueDeclare("queue.pc", true, false, false, null);
        channel.exchangeDeclare("ex.pc", "direct", true, false, null);

        channel.queueBind("queue.pc", "ex.pc", "key.pc");

        ConcurrentSkipListMap<Long, String> outStandConfirms = new ConcurrentSkipListMap<>();

        ConfirmCallback clearOutStandConfirms = (deliveryTag, multiple) -> {
            if (multiple) {
                System.out.println("编号小于等于" + deliveryTag + "的消息都已经被确认了");
                final ConcurrentNavigableMap<Long, String> headMap = outStandConfirms.headMap(deliveryTag, true);
                // 清空outstandingConfirms 中已经被确认的消息
                headMap.clear();
            } else {
                outStandConfirms.remove(deliveryTag);
                System.out.println("编号为" + deliveryTag + "的消息被确认");
            }
        };
        // 设置channel 的监听器，处理确认和不确认的消息
        channel.addConfirmListener(clearOutStandConfirms, (deliveryTag, multiple) -> {
            if (multiple) {
                // 将没有确认的消息记录到一个集合中
                // 此处省略实现
                System.out.println("消息编号小于等于" + deliveryTag + "的消息不确认");
            } else {
                System.out.println("编号为" + deliveryTag + "的消息被确认");
            }
        });

        String message = "hello-";
        for (int i = 0; i < 1000; i++) {
            // 获取下一条即将发送的消息的消息ID
            final long nextPublishSeqNo = channel.getNextPublishSeqNo();
            channel.basicPublish("ex.pc", "key.pc", null, (message + i).getBytes());
            System.out.println("编号为：" + nextPublishSeqNo + "的消息已经发送成功，尚未确认");
            outStandConfirms.put(nextPublishSeqNo, (message + i));
        }


        // 等待消息确认
        Thread.sleep(1000);

        channel.close();
        connection.close();

    }
}
