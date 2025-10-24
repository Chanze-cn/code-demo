package cn.chanze.codedemo;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * BlockingQueue 高并发测试演示类
 * 演示不同BlockingQueue实现的高并发场景测试
 */
public class BlockingQueueDemo {
    
    private final BlockingQueue<Integer> queue;
    private final int capacity;
    private final AtomicInteger producerCount = new AtomicInteger(0);
    private final AtomicInteger consumerCount = new AtomicInteger(0);
    private final AtomicLong totalProduced = new AtomicLong(0);
    private final AtomicLong totalConsumed = new AtomicLong(0);
    private final AtomicLong totalSum = new AtomicLong(0);
    
    public BlockingQueueDemo(BlockingQueue<Integer> queue, int capacity) {
        this.queue = queue;
        this.capacity = capacity;
    }
    
    /**
     * 初始化队列 - 将队列填满
     * @param initialData 初始数据
     */
    public void initializeQueue(int[] initialData) {
        System.out.println("=== 初始化队列 ===");
        System.out.println("队列容量: " + capacity);
        System.out.println("初始数据量: " + initialData.length);
        
        try {
            for (int data : initialData) {
                queue.put(data);
            }
            System.out.println("✅ 队列初始化完成，当前大小: " + queue.size());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("❌ 队列初始化被中断");
        }
    }
    
    /**
     * 高并发生产者测试
     * @param producerCount 生产者数量
     * @param itemsPerProducer 每个生产者生产的项目数
     * @param timeoutSeconds 超时时间（秒）
     */
    public void testConcurrentProducers(int producerCount, int itemsPerProducer, int timeoutSeconds) {
        System.out.println("\n=== 高并发生产者测试 ===");
        System.out.println("生产者数量: " + producerCount);
        System.out.println("每生产者项目数: " + itemsPerProducer);
        System.out.println("总生产项目数: " + (producerCount * itemsPerProducer));
        
        this.producerCount.set(producerCount);
        this.totalProduced.set(0);
        
        Thread[] producers = new Thread[producerCount];
        long startTime = System.currentTimeMillis();
        
        // 创建生产者线程
        for (int i = 0; i < producerCount; i++) {
            final int producerId = i;
            producers[i] = new Thread(() -> {
                try {
                    for (int j = 0; j < itemsPerProducer; j++) {
                        int item = producerId * itemsPerProducer + j;
                        queue.put(item);
                        totalProduced.incrementAndGet();
                        totalSum.addAndGet(item);
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    System.err.println("生产者 " + producerId + " 被中断");
                }
            });
        }
        
        // 启动所有生产者
        for (Thread producer : producers) {
            producer.start();
        }
        
        // 等待所有生产者完成或超时
        try {
            for (Thread producer : producers) {
                producer.join(timeoutSeconds * 1000);
                if (producer.isAlive()) {
                    System.err.println("⚠️ 生产者超时，强制中断");
                    producer.interrupt();
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        long endTime = System.currentTimeMillis();
        System.out.println("✅ 生产者测试完成");
        System.out.println("实际生产项目数: " + totalProduced.get());
        System.out.println("队列当前大小: " + queue.size());
        System.out.println("耗时: " + (endTime - startTime) + "ms");
    }
    
    /**
     * 高并发消费者测试
     * @param consumerCount 消费者数量
     * @param itemsPerConsumer 每个消费者消费的项目数
     * @param timeoutSeconds 超时时间（秒）
     */
    public void testConcurrentConsumers(int consumerCount, int itemsPerConsumer, int timeoutSeconds) {
        System.out.println("\n=== 高并发消费者测试 ===");
        System.out.println("消费者数量: " + consumerCount);
        System.out.println("每消费者项目数: " + itemsPerConsumer);
        System.out.println("总消费项目数: " + (consumerCount * itemsPerConsumer));
        
        this.consumerCount.set(consumerCount);
        this.totalConsumed.set(0);
        
        Thread[] consumers = new Thread[consumerCount];
        long startTime = System.currentTimeMillis();
        
        // 创建消费者线程
        for (int i = 0; i < consumerCount; i++) {
            final int consumerId = i;
            consumers[i] = new Thread(() -> {
                try {
                    for (int j = 0; j < itemsPerConsumer; j++) {
                        Integer item = queue.take();
                        if (item != null) {
                            totalConsumed.incrementAndGet();
                            totalSum.addAndGet(-item); // 从总和中减去消费的项目
                        }
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    System.err.println("消费者 " + consumerId + " 被中断");
                }
            });
        }
        
        // 启动所有消费者
        for (Thread consumer : consumers) {
            consumer.start();
        }
        
        // 等待所有消费者完成或超时
        try {
            for (Thread consumer : consumers) {
                consumer.join(timeoutSeconds * 1000);
                if (consumer.isAlive()) {
                    System.err.println("⚠️ 消费者超时，强制中断");
                    consumer.interrupt();
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        long endTime = System.currentTimeMillis();
        System.out.println("✅ 消费者测试完成");
        System.out.println("实际消费项目数: " + totalConsumed.get());
        System.out.println("队列当前大小: " + queue.size());
        System.out.println("耗时: " + (endTime - startTime) + "ms");
    }
    
    /**
     * 混合高并发测试 - 同时进行生产和消费
     * @param producerCount 生产者数量
     * @param consumerCount 消费者数量
     * @param itemsPerProducer 每个生产者生产的项目数
     * @param itemsPerConsumer 每个消费者消费的项目数
     * @param timeoutSeconds 超时时间（秒）
     */
    public void testMixedConcurrency(int producerCount, int consumerCount, 
                                   int itemsPerProducer, int itemsPerConsumer, 
                                   int timeoutSeconds) {
        System.out.println("\n=== 混合高并发测试 ===");
        System.out.println("生产者数量: " + producerCount);
        System.out.println("消费者数量: " + consumerCount);
        System.out.println("每生产者项目数: " + itemsPerProducer);
        System.out.println("每消费者项目数: " + itemsPerConsumer);
        
        this.producerCount.set(producerCount);
        this.consumerCount.set(consumerCount);
        this.totalProduced.set(0);
        this.totalConsumed.set(0);
        
        Thread[] producers = new Thread[producerCount];
        Thread[] consumers = new Thread[consumerCount];
        long startTime = System.currentTimeMillis();
        
        // 创建生产者线程
        for (int i = 0; i < producerCount; i++) {
            final int producerId = i;
            producers[i] = new Thread(() -> {
                try {
                    for (int j = 0; j < itemsPerProducer; j++) {
                        int item = producerId * itemsPerProducer + j;
                        queue.put(item);
                        totalProduced.incrementAndGet();
                        totalSum.addAndGet(item);
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }
        
        // 创建消费者线程
        for (int i = 0; i < consumerCount; i++) {
            final int consumerId = i;
            consumers[i] = new Thread(() -> {
                try {
                    for (int j = 0; j < itemsPerConsumer; j++) {
                        Integer item = queue.take();
                        if (item != null) {
                            totalConsumed.incrementAndGet();
                            totalSum.addAndGet(-item);
                        }
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }
        
        // 启动所有线程
        for (Thread producer : producers) {
            producer.start();
        }
        for (Thread consumer : consumers) {
            consumer.start();
        }
        
        // 等待所有线程完成
        try {
            for (Thread producer : producers) {
                producer.join(timeoutSeconds * 1000);
            }
            for (Thread consumer : consumers) {
                consumer.join(timeoutSeconds * 1000);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        long endTime = System.currentTimeMillis();
        System.out.println("✅ 混合并发测试完成");
        System.out.println("生产项目数: " + totalProduced.get());
        System.out.println("消费项目数: " + totalConsumed.get());
        System.out.println("队列当前大小: " + queue.size());
        System.out.println("总和数据一致性: " + (totalSum.get() == 0 ? "✅ 通过" : "❌ 失败"));
        System.out.println("耗时: " + (endTime - startTime) + "ms");
    }
    
    /**
     * 压力测试 - 测试队列在极限情况下的表现
     * @param threadCount 线程数量
     * @param operationsPerThread 每线程操作数
     * @param timeoutSeconds 超时时间（秒）
     */
    public void stressTest(int threadCount, int operationsPerThread, int timeoutSeconds) {
        System.out.println("\n=== 压力测试 ===");
        System.out.println("线程数量: " + threadCount);
        System.out.println("每线程操作数: " + operationsPerThread);
        System.out.println("总操作数: " + (threadCount * operationsPerThread));
        
        Thread[] threads = new Thread[threadCount];
        long startTime = System.currentTimeMillis();
        
        // 创建混合操作线程（一半生产，一半消费）
        for (int i = 0; i < threadCount; i++) {
            final int threadId = i;
            final boolean isProducer = i % 2 == 0;
            
            threads[i] = new Thread(() -> {
                try {
                    for (int j = 0; j < operationsPerThread; j++) {
                        if (isProducer) {
                            int item = threadId * operationsPerThread + j;
                            queue.put(item);
                            totalProduced.incrementAndGet();
                            totalSum.addAndGet(item);
                        } else {
                            Integer item = queue.take();
                            if (item != null) {
                                totalConsumed.incrementAndGet();
                                totalSum.addAndGet(-item);
                            }
                        }
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }
        
        // 启动所有线程
        for (Thread thread : threads) {
            thread.start();
        }
        
        // 等待所有线程完成
        try {
            for (Thread thread : threads) {
                thread.join(timeoutSeconds * 1000);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        long endTime = System.currentTimeMillis();
        System.out.println("✅ 压力测试完成");
        System.out.println("生产项目数: " + totalProduced.get());
        System.out.println("消费项目数: " + totalConsumed.get());
        System.out.println("队列当前大小: " + queue.size());
        System.out.println("总和数据一致性: " + (totalSum.get() == 0 ? "✅ 通过" : "❌ 失败"));
        System.out.println("耗时: " + (endTime - startTime) + "ms");
        System.out.println("吞吐量: " + (totalProduced.get() + totalConsumed.get()) / ((endTime - startTime) / 1000.0) + " 操作/秒");
    }
    
    /**
     * 获取队列当前状态
     */
    public void printQueueStatus() {
        System.out.println("\n=== 队列状态 ===");
        System.out.println("队列类型: " + queue.getClass().getSimpleName());
        System.out.println("当前大小: " + queue.size());
        System.out.println("剩余容量: " + (capacity - queue.size()));
        System.out.println("是否为空: " + queue.isEmpty());
        System.out.println("是否已满: " + (queue.size() >= capacity));
    }
    
    /**
     * 清空队列
     */
    public void clearQueue() {
        queue.clear();
        totalProduced.set(0);
        totalConsumed.set(0);
        totalSum.set(0);
        System.out.println("✅ 队列已清空");
    }
    
    // Getter方法供测试使用
    public BlockingQueue<Integer> getQueue() {
        return queue;
    }
    
    public AtomicLong getTotalProduced() {
        return totalProduced;
    }
    
    public AtomicLong getTotalConsumed() {
        return totalConsumed;
    }
    
    public AtomicLong getTotalSum() {
        return totalSum;
    }
}