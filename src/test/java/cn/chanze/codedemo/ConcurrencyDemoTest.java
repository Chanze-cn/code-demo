package cn.chanze.codedemo;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

/**
 * ConcurrencyDemo类的单元测试
 * 测试并发编程相关的功能
 */
public class ConcurrencyDemoTest {
    
    private ConcurrencyDemo concurrencyDemo;
    
    @BeforeEach
    public void setUp() {
        concurrencyDemo = new ConcurrencyDemo();
    }
    
    @AfterEach
    public void tearDown() {
        concurrencyDemo.shutdown();
    }
    
    @Test
    @Timeout(value = 10, unit = TimeUnit.SECONDS)
    public void testThreadSafetyIssue() {
        // 这个测试验证线程安全问题确实存在
        // 由于竞态条件，结果可能小于期望值
        assertDoesNotThrow(() -> {
            concurrencyDemo.demonstrateThreadSafetyIssue();
        });
    }
    
    @Test
    @Timeout(value = 10, unit = TimeUnit.SECONDS)
    public void testSynchronizedSolution() {
        // 测试synchronized解决方案
        assertDoesNotThrow(() -> {
            concurrencyDemo.demonstrateSynchronizedSolution();
        });
    }
    
    @Test
    @Timeout(value = 10, unit = TimeUnit.SECONDS)
    public void testAtomicSolution() {
        // 测试原子操作解决方案
        assertDoesNotThrow(() -> {
            concurrencyDemo.demonstrateAtomicSolution();
        });
    }
    
    @Test
    @Timeout(value = 10, unit = TimeUnit.SECONDS)
    public void testReentrantLock() {
        // 测试ReentrantLock解决方案
        assertDoesNotThrow(() -> {
            concurrencyDemo.demonstrateReentrantLock();
        });
    }
    
    @Test
    @Timeout(value = 15, unit = TimeUnit.SECONDS)
    public void testProducerConsumer() {
        // 测试生产者消费者模式
        assertDoesNotThrow(() -> {
            concurrencyDemo.demonstrateProducerConsumer();
        });
    }
    
    @Test
    @Timeout(value = 10, unit = TimeUnit.SECONDS)
    public void testCompletableFuture() {
        // 测试CompletableFuture异步编程
        assertDoesNotThrow(() -> {
            concurrencyDemo.demonstrateCompletableFuture();
        });
    }
    
    @Test
    @Timeout(value = 10, unit = TimeUnit.SECONDS)
    public void testThreadPool() {
        // 测试线程池使用
        assertDoesNotThrow(() -> {
            concurrencyDemo.demonstrateThreadPool();
        });
    }
    
    @Test
    @Timeout(value = 30, unit = TimeUnit.SECONDS)
    public void testRunAllDemonstrations() {
        // 测试运行所有演示
        assertDoesNotThrow(() -> {
            concurrencyDemo.runAllDemonstrations();
        });
    }
    
    @Test
    public void testShutdown() {
        // 测试资源关闭
        assertDoesNotThrow(() -> {
            concurrencyDemo.shutdown();
        });
    }
    
    /**
     * 测试原子操作的正确性
     * 验证AtomicInteger在并发环境下的正确性
     */
    @Test
    @Timeout(value = 10, unit = TimeUnit.SECONDS)
    public void testAtomicIntegerCorrectness() {
        AtomicInteger counter = new AtomicInteger(0);
        int threadCount = 100;
        int operationsPerThread = 1000;
        CountDownLatch latch = new CountDownLatch(threadCount);
        
        ExecutorService executor = Executors.newFixedThreadPool(10);
        
        // 创建多个线程同时增加原子计数器
        for (int i = 0; i < threadCount; i++) {
            executor.submit(() -> {
                try {
                    for (int j = 0; j < operationsPerThread; j++) {
                        counter.incrementAndGet();
                    }
                } finally {
                    latch.countDown();
                }
            });
        }
        
        try {
            latch.await();
            int expectedValue = threadCount * operationsPerThread;
            assertEquals(expectedValue, counter.get(), 
                "原子计数器应该等于线程数乘以每线程操作数");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            fail("测试被中断");
        } finally {
            executor.shutdown();
        }
    }
    
    /**
     * 测试synchronized方法的线程安全性
     */
    @Test
    @Timeout(value = 10, unit = TimeUnit.SECONDS)
    public void testSynchronizedMethodThreadSafety() {
        // 创建一个简单的同步计数器类进行测试
        class SynchronizedCounter {
            private int count = 0;
            
            public synchronized void increment() {
                count++;
            }
            
            public synchronized int getCount() {
                return count;
            }
        }
        
        SynchronizedCounter counter = new SynchronizedCounter();
        int threadCount = 50;
        int operationsPerThread = 100;
        CountDownLatch latch = new CountDownLatch(threadCount);
        
        ExecutorService executor = Executors.newFixedThreadPool(10);
        
        // 创建多个线程同时增加同步计数器
        for (int i = 0; i < threadCount; i++) {
            executor.submit(() -> {
                try {
                    for (int j = 0; j < operationsPerThread; j++) {
                        counter.increment();
                    }
                } finally {
                    latch.countDown();
                }
            });
        }
        
        try {
            latch.await();
            int expectedValue = threadCount * operationsPerThread;
            assertEquals(expectedValue, counter.getCount(), 
                "同步计数器应该等于线程数乘以每线程操作数");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            fail("测试被中断");
        } finally {
            executor.shutdown();
        }
    }
}