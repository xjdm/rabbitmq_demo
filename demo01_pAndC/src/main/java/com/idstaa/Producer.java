package com.idstaa;

import java.util.UUID;
import java.util.concurrent.BlockingQueue;

/**
 * @author chenjie
 * @date 2021/2/18 21:43
 */
public class Producer implements Runnable {
    private final BlockingQueue<KouZhao> blockingQueue;

    public Producer(BlockingQueue<KouZhao> blockingQueue) {
        this.blockingQueue = blockingQueue;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(200);
                if (blockingQueue.remainingCapacity() > 0) {
                    KouZhao kouZhao = new KouZhao(UUID.randomUUID().toString(), "N95");
                    blockingQueue.add(kouZhao);
                    System.out.println("我在生产口罩，当前库存是：" + blockingQueue.size());
                } else {
                    System.out.println("我的仓库已经堆满了" + blockingQueue.size() + "个口罩，快来买口罩啊！");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
