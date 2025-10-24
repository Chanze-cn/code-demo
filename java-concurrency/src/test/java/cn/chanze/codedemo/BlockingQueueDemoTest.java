package cn.chanze.codedemo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.api.DisplayName;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import static org.junit.jupiter.api.Assertions.*;

/**
 * BlockingQueueDemo 高并发测试类
 * 测试不同BlockingQueue实现的高并发场景
 */
public class BlockingQueueDemoTest {
    
    private static final int CAPACITY = 100;
    private static final int TIMEOUT_SECONDS = 30;
    
    private BlockingQueueDemo arrayQueueDemo;
    private BlockingQueueDemo linkedQueueDemo;
    private BlockingQueueDemo priorityQueueDemo;
    
    @BeforeEach
    public void setUp() {
        // 创建不同类型的BlockingQueue测试实例
        arrayQueueDemo = new BlockingQueueDemo(new ArrayBlockingQueue<>(CAPACITY), CAPACITY);
        linkedQueueDemo = new BlockingQueueDemo(new LinkedBlockingQueue<>(CAPACITY), CAPACITY);
        priorityQueueDemo = new BlockingQueueDemo(new PriorityBlockingQueue<>(CAPACITY), CAPACITY);
    }
    
    @Test
    @DisplayName("测试ArrayBlockingQueue初始化")
    @Timeout(value = 10, unit = TimeUnit.SECONDS)
    public void testArrayBlockingQueueInitialization() {
        // 准备初始数据
        int[] initialData = new int[CAPACITY];
        for (int i = 0; i < CAPACITY; i++) {
            initialData[i] = i;
        }
        
        // 初始化队列
        arrayQueueDemo.initializeQueue(initialData);
        
        // 验证队列状态
        arrayQueueDemo.printQueueStatus();
        assertEquals(CAPACITY, ((ArrayBlockingQueue<Integer>) arrayQueueDemo.getQueue()).size());
    }
    
    @Test
    @DisplayName("测试ArrayBlockingQueue高并发生产者")
    @Timeout(value = 30, unit = TimeUnit.SECONDS)
    public void testArrayBlockingQueueConcurrentProducers() {
        // 先清空队列
        arrayQueueDemo.clearQueue();
        
        // 高并发生产者测试
        int producerCount = 10;
        int itemsPerProducer = 50;
        
        arrayQueueDemo.testConcurrentProducers(producerCount, itemsPerProducer, TIMEOUT_SECONDS);
        
        // 验证结果
        int expectedTotal = producerCount * itemsPerProducer;
        assertEquals(expectedTotal, arrayQueueDemo.getTotalProduced().get());
        assertEquals(expectedTotal, arrayQueueDemo.getQueue().size());
    }
    
    @Test
    @DisplayName("测试ArrayBlockingQueue高并发消费者")
    @Timeout(value = 30, unit = TimeUnit.SECONDS)
    public void testArrayBlockingQueueConcurrentConsumers() {
        // 先填满队列
        int[] initialData = new int[CAPACITY];
        for (int i = 0; i < CAPACITY; i++) {
            initialData[i] = i;
        }
        arrayQueueDemo.initializeQueue(initialData);
        
        // 高并发消费者测试
        int consumerCount = 10;
        int itemsPerConsumer = 10;
        
        arrayQueueDemo.testConcurrentConsumers(consumerCount, itemsPerConsumer, TIMEOUT_SECONDS);
        
        // 验证结果
        int expectedConsumed = consumerCount * itemsPerConsumer;
        assertEquals(expectedConsumed, arrayQueueDemo.getTotalConsumed().get());
        assertEquals(CAPACITY - expectedConsumed, arrayQueueDemo.getQueue().size());
    }
    
    @Test
    @DisplayName("测试ArrayBlockingQueue混合高并发")
    @Timeout(value = 30, unit = TimeUnit.SECONDS)
    public void testArrayBlockingQueueMixedConcurrency() {
        // 清空队列
        arrayQueueDemo.clearQueue();
        
        // 混合高并发测试
        int producerCount = 5;
        int consumerCount = 5;
        int itemsPerProducer = 20;
        int itemsPerConsumer = 20;
        
        arrayQueueDemo.testMixedConcurrency(producerCount, consumerCount, 
                                          itemsPerProducer, itemsPerConsumer, TIMEOUT_SECONDS);
        
        // 验证数据一致性
        int expectedProduced = producerCount * itemsPerProducer;
        int expectedConsumed = consumerCount * itemsPerConsumer;
        
        assertEquals(expectedProduced, arrayQueueDemo.getTotalProduced().get());
        assertEquals(expectedConsumed, arrayQueueDemo.getTotalConsumed().get());
        
        // 验证总和数据一致性（生产的总和应该等于消费的总和）
        assertEquals(0, arrayQueueDemo.getTotalSum().get());
    }
    
    @Test
    @DisplayName("测试ArrayBlockingQueue压力测试")
    @Timeout(value = 60, unit = TimeUnit.SECONDS)
    public void testArrayBlockingQueueStressTest() {
        // 清空队列
        arrayQueueDemo.clearQueue();
        
        // 压力测试
        int threadCount = 20;
        int operationsPerThread = 100;
        
        arrayQueueDemo.stressTest(threadCount, operationsPerThread, TIMEOUT_SECONDS);
        
        // 验证数据一致性
        assertEquals(0, arrayQueueDemo.getTotalSum().get());
        
        // 验证吞吐量合理性（应该大于0）
        assertTrue(arrayQueueDemo.getTotalProduced().get() > 0);
        assertTrue(arrayQueueDemo.getTotalConsumed().get() > 0);
    }
    
    @Test
    @DisplayName("测试LinkedBlockingQueue高并发")
    @Timeout(value = 30, unit = TimeUnit.SECONDS)
    public void testLinkedBlockingQueueConcurrency() {
        // 清空队列
        linkedQueueDemo.clearQueue();
        
        // 混合高并发测试
        int producerCount = 8;
        int consumerCount = 8;
        int itemsPerProducer = 25;
        int itemsPerConsumer = 25;
        
        linkedQueueDemo.testMixedConcurrency(producerCount, consumerCount, 
                                           itemsPerProducer, itemsPerConsumer, TIMEOUT_SECONDS);
        
        // 验证数据一致性
        assertEquals(0, linkedQueueDemo.getTotalSum().get());
    }
    
    @Test
    @DisplayName("测试PriorityBlockingQueue高并发")
    @Timeout(value = 30, unit = TimeUnit.SECONDS)
    public void testPriorityBlockingQueueConcurrency() {
        // 清空队列
        priorityQueueDemo.clearQueue();
        
        // 混合高并发测试
        int producerCount = 6;
        int consumerCount = 6;
        int itemsPerProducer = 30;
        int itemsPerConsumer = 30;
        
        priorityQueueDemo.testMixedConcurrency(producerCount, consumerCount, 
                                             itemsPerProducer, itemsPerConsumer, TIMEOUT_SECONDS);
        
        // 验证数据一致性
        assertEquals(0, priorityQueueDemo.getTotalSum().get());
    }
    
    @Test
    @DisplayName("测试队列线程安全性 - 数据竞争检测")
    @Timeout(value = 30, unit = TimeUnit.SECONDS)
    public void testQueueThreadSafety() {
        // 使用较小的队列容量来增加竞争
        BlockingQueueDemo smallQueueDemo = new BlockingQueueDemo(
            new ArrayBlockingQueue<>(10), 10);
        
        // 创建大量线程进行竞争
        int threadCount = 50;
        int operationsPerThread = 20;
        
        smallQueueDemo.stressTest(threadCount, operationsPerThread, TIMEOUT_SECONDS);
        
        // 验证没有数据丢失或重复
        assertEquals(0, smallQueueDemo.getTotalSum().get());
        
        // 验证队列大小在合理范围内
        assertTrue(smallQueueDemo.getQueue().size() <= 10);
    }
    
    @Test
    @DisplayName("测试队列阻塞行为")
    @Timeout(value = 10, unit = TimeUnit.SECONDS)
    public void testQueueBlockingBehavior() throws InterruptedException {
        // 创建一个容量为1的队列
        BlockingQueue<Integer> smallQueue = new ArrayBlockingQueue<>(1);
        
        // 先填满队列
        smallQueue.put(1);
        
        // 测试生产者阻塞
        Thread producer = new Thread(() -> {
            try {
                smallQueue.put(2); // 这应该阻塞
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        
        producer.start();
        
        // 等待一小段时间确保线程开始阻塞
        Thread.sleep(100);
        
        // 验证生产者线程仍在运行（被阻塞）
        assertTrue(producer.isAlive());
        
        // 消费一个元素，应该解除阻塞
        Integer item = smallQueue.take();
        assertEquals(Integer.valueOf(1), item);
        
        // 等待生产者完成
        producer.join(1000);
        
        // 验证队列中有新元素
        assertEquals(1, smallQueue.size());
        assertEquals(Integer.valueOf(2), smallQueue.peek());
    }
    
    @Test
    @DisplayName("测试队列超时行为")
    @Timeout(value = 10, unit = TimeUnit.SECONDS)
    public void testQueueTimeoutBehavior() throws InterruptedException {
        BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(1);
        
        // 填满队列
        queue.put(1);
        
        // 测试超时put
        boolean putResult = queue.offer(2, 1, TimeUnit.SECONDS);
        assertFalse(putResult, "队列已满，put应该超时失败");
        
        // 测试超时take
        Integer item = queue.poll(1, TimeUnit.SECONDS);
        assertNotNull(item, "应该能成功取出元素");
        assertEquals(Integer.valueOf(1), item);
        
        // 测试空队列的超时take
        item = queue.poll(1, TimeUnit.SECONDS);
        assertNull(item, "空队列的take应该超时返回null");
    }
    
    @Test
    @DisplayName("测试队列容量限制")
    @Timeout(value = 10, unit = TimeUnit.SECONDS)
    public void testQueueCapacityLimit() {
        BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(5);
        
        // 测试容量限制
        for (int i = 0; i < 5; i++) {
            assertTrue(queue.offer(i), "应该能成功添加元素 " + i);
        }
        
        // 第6个元素应该失败
        assertFalse(queue.offer(5), "队列已满，添加应该失败");
        
        // 验证队列大小
        assertEquals(5, queue.size());
        
        // 移除一个元素后应该能添加
        queue.poll();
        assertTrue(queue.offer(6), "移除元素后应该能添加新元素");
    }
    
    @Test
    @DisplayName("测试多队列并发性能对比")
    @Timeout(value = 60, unit = TimeUnit.SECONDS)
    public void testMultipleQueuePerformanceComparison() {
        System.out.println("\n=== 多队列性能对比测试 ===");
        
        int threadCount = 20;
        int operationsPerThread = 50;
        
        // 测试ArrayBlockingQueue
        long arrayStartTime = System.currentTimeMillis();
        arrayQueueDemo.clearQueue();
        arrayQueueDemo.stressTest(threadCount, operationsPerThread, TIMEOUT_SECONDS);
        long arrayEndTime = System.currentTimeMillis();
        
        // 测试LinkedBlockingQueue
        long linkedStartTime = System.currentTimeMillis();
        linkedQueueDemo.clearQueue();
        linkedQueueDemo.stressTest(threadCount, operationsPerThread, TIMEOUT_SECONDS);
        long linkedEndTime = System.currentTimeMillis();
        
        // 测试PriorityBlockingQueue
        long priorityStartTime = System.currentTimeMillis();
        priorityQueueDemo.clearQueue();
        priorityQueueDemo.stressTest(threadCount, operationsPerThread, TIMEOUT_SECONDS);
        long priorityEndTime = System.currentTimeMillis();
        
        System.out.println("ArrayBlockingQueue 耗时: " + (arrayEndTime - arrayStartTime) + "ms");
        System.out.println("LinkedBlockingQueue 耗时: " + (linkedEndTime - linkedStartTime) + "ms");
        System.out.println("PriorityBlockingQueue 耗时: " + (priorityEndTime - priorityStartTime) + "ms");
        
        // 验证所有队列都完成了测试
        assertEquals(0, arrayQueueDemo.getTotalSum().get());
        assertEquals(0, linkedQueueDemo.getTotalSum().get());
        assertEquals(0, priorityQueueDemo.getTotalSum().get());
    }
}