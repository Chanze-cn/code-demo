package cn.chanze.monitor;

import org.junit.jupiter.api.Assertions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * BlockQueue 测试类
 * 重点测试高并发场景下的线程安全问题
 */
public class BlockQueueTest {

    private BlockQueue<Integer> queue;
    private final int CAPACITY = 5;

    @BeforeEach
    void setUp() throws InterruptedException {
        queue = new BlockQueue<>(CAPACITY);
        //预填充队列
        for (int i = 0; i < CAPACITY; i++) {
            queue.enqueue(i);
        }
    }

    /**
     * 测试多线程入队和出队
     * @throws InterruptedException 中断异常
     */
    @Test
    public void testMultiThreadEnqueueAndDequeue() throws InterruptedException {
        //首先创建一个数据的生产者
        Runnable proRunnable = () -> {
            for (int i = 0; i < 10; i++) {
                try {
                    queue.enqueue(i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        //然后创建一个数据消费者
        Runnable conRunnable = () -> {
            for (int i = 0; i < 10; i++) {
                try {
                    queue.dequeue();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        //创建几个生产者线程，几个消费者线程
        Thread proThread1 = new Thread(proRunnable);
        Thread proThread2 = new Thread(proRunnable);
        Thread conThread1 = new Thread(conRunnable);
        Thread conThread2 = new Thread(conRunnable);
        //启动线程
        proThread1.start();
        proThread2.start();
        conThread1.start();
        conThread2.start();
        //等待线程结束
        proThread1.join();
        proThread2.join();
        conThread1.join();
        conThread2.join();
    }


}
