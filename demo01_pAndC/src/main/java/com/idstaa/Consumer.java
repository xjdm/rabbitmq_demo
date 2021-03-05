package com.idstaa;

import java.util.concurrent.BlockingQueue;

/**
 * @author chenjie
 * @date 2021/2/18 21:44
 */
public class Consumer implements Runnable {
    private final BlockingQueue<KouZhao> blockingQueue;

    public Consumer(BlockingQueue<KouZhao> blockingQueue) {
        this.blockingQueue = blockingQueue;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(100);
                // 获取开始时间
                long startTime = System.currentTimeMillis();
                KouZhao kouZhao = blockingQueue.take();
                // 获取结束时间
                long endTime = System.currentTimeMillis();
                System.out.println("我消费了口罩：" + kouZhao + ", 等到货时我阻 塞了" + (endTime - startTime) + "ms");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
