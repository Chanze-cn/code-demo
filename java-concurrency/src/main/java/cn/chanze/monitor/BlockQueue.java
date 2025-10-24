package cn.chanze.monitor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 阻塞队列，用于演示管程的示例
 * 入队时，如果队列已满，则阻塞等待
 * 出队时，如果队列已空，则阻塞等待
 */
public class BlockQueue<T> {

    /**
     * 队列
     */
    private final List<T> queue = new ArrayList<>();

    /**
     * 队列容量
     */
    private final int capacity;

    /**
     * 锁
     */
    private final Lock lock = new ReentrantLock();

    /**
     * 不满条件
     */
    private final Condition notFull = lock.newCondition();

    /**
     * 不空条件
     */
    private final Condition notEmpty = lock.newCondition();

    /**
     * 构造函数
     * @param capacity 队列容量
     */
    public BlockQueue(int capacity) {
        this.capacity = capacity;
    }

    /**
     * 入队
     * @param t 元素
     * @throws InterruptedException 中断异常
     */
    public void enqueue(T t) throws InterruptedException {
        lock.lock();
        try {
            while (queue.size() >= capacity) {
                // 如果队列已满，则阻塞等待
                System.out.println("【"+Thread.currentThread().getName()+"】 尝试入队元素：【"+t+"】，请求入队，此时队列已满，阻塞等待");
                notFull.await();
            }
            // 入队
            System.out.println("【"+Thread.currentThread().getName()+"】 尝试入队元素：【"+t+"】，入队成功");
            queue.add(t);
            // 已有数据，通知所有等待出队的线程
            notEmpty.signalAll();
        } finally {
            lock.unlock();
        }
    }
    
    /**
     * 出队方法
     * @return 元素
     * @throws InterruptedException 中断异常
     */
    public T dequeue() throws InterruptedException {
        lock.lock();
        try {
            while (queue.isEmpty()) {
                // 如果队列已空，则阻塞等待
                System.out.println("【"+Thread.currentThread().getName()+"】 请求出队，此时队列已空，阻塞等待");
                notEmpty.await();
            }
            // 出队
            System.out.println("【"+Thread.currentThread().getName()+"】 请求出队，元素为：【"+queue.get(0)+"】，出队成功");
            T result = queue.remove(0);
            // 队列已有空位，通知所有等待入队的线程
            notFull.signalAll();
            return result;
        } finally {
            lock.unlock();
        }
    }
}
