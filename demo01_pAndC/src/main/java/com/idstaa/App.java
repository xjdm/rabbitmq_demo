package com.idstaa;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * @author chenjie
 * @date 2021/2/18 21:45
 */
public class App {
    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<KouZhao> queue = new ArrayBlockingQueue<>(20);
        Producer producer = new Producer(queue);
        Consumer consumer = new Consumer(queue);
        new Thread(producer).start();
        Thread.sleep(5000);
        new Thread(consumer).start();
    }
}
