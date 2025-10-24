package cn.chanze.codedemo;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Java高并发编程学习示例
 * 演示多线程、同步机制、原子操作、并发集合等概念
 */
public class ConcurrencyDemo {
    
    // 普通计数器（存在线程安全问题）
    private int normalCounter = 0;
    
    // 原子计数器（线程安全）
    private AtomicInteger atomicCounter = new AtomicInteger(0);
    
    // 可重入锁
    private final Lock lock = new ReentrantLock();
    
    // 线程池
    private final ExecutorService executorService = Executors.newFixedThreadPool(10);
    
    /**
     * 演示线程安全问题
     * 多个线程同时修改普通变量会导致数据不一致
     */
    public void demonstrateThreadSafetyIssue() {
        System.out.println("=== 演示线程安全问题 ===");
        
        normalCounter = 0;
        int threadCount = 1000;
        CountDownLatch latch = new CountDownLatch(threadCount);
        
        // 创建多个线程同时增加计数器
        for (int i = 0; i < threadCount; i++) {
            executorService.submit(() -> {
                try {
                    normalCounter++; // 非原子操作，存在竞态条件
                } finally {
                    latch.countDown();
                }
            });
        }
        
        try {
            latch.await(); // 等待所有线程完成
            System.out.println("期望结果: " + threadCount);
            System.out.println("实际结果: " + normalCounter);
            System.out.println("数据丢失: " + (threadCount - normalCounter));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
    /**
     * 演示synchronized关键字解决线程安全问题
     */
    public void demonstrateSynchronizedSolution() {
        System.out.println("\n=== 使用synchronized解决线程安全问题 ===");
        
        normalCounter = 0;
        int threadCount = 1000;
        CountDownLatch latch = new CountDownLatch(threadCount);
        
        for (int i = 0; i < threadCount; i++) {
            executorService.submit(() -> {
                try {
                    synchronized (this) { // 同步块
                        normalCounter++;
                    }
                } finally {
                    latch.countDown();
                }
            });
        }
        
        try {
            latch.await();
            System.out.println("期望结果: " + threadCount);
            System.out.println("实际结果: " + normalCounter);
            System.out.println("数据一致性: " + (normalCounter == threadCount));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
    /**
     * 演示原子操作类解决线程安全问题
     */
    public void demonstrateAtomicSolution() {
        System.out.println("\n=== 使用原子操作类解决线程安全问题 ===");
        
        atomicCounter.set(0);
        int threadCount = 1000;
        CountDownLatch latch = new CountDownLatch(threadCount);
        
        for (int i = 0; i < threadCount; i++) {
            executorService.submit(() -> {
                try {
                    atomicCounter.incrementAndGet(); // 原子操作
                } finally {
                    latch.countDown();
                }
            });
        }
        
        try {
            latch.await();
            System.out.println("期望结果: " + threadCount);
            System.out.println("实际结果: " + atomicCounter.get());
            System.out.println("数据一致性: " + (atomicCounter.get() == threadCount));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
    /**
     * 演示ReentrantLock的使用
     */
    public void demonstrateReentrantLock() {
        System.out.println("\n=== 演示ReentrantLock ===");
        
        normalCounter = 0;
        int threadCount = 1000;
        CountDownLatch latch = new CountDownLatch(threadCount);
        
        for (int i = 0; i < threadCount; i++) {
            executorService.submit(() -> {
                try {
                    lock.lock(); // 获取锁
                    try {
                        normalCounter++;
                    } finally {
                        lock.unlock(); // 释放锁
                    }
                } finally {
                    latch.countDown();
                }
            });
        }
        
        try {
            latch.await();
            System.out.println("期望结果: " + threadCount);
            System.out.println("实际结果: " + normalCounter);
            System.out.println("数据一致性: " + (normalCounter == threadCount));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
    /**
     * 演示生产者消费者模式
     */
    public void demonstrateProducerConsumer() {
        System.out.println("\n=== 演示生产者消费者模式 ===");
        
        BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(10);
        int producerCount = 3;
        int consumerCount = 2;
        CountDownLatch latch = new CountDownLatch(producerCount + consumerCount);
        
        // 生产者线程
        for (int i = 0; i < producerCount; i++) {
            final int producerId = i;
            executorService.submit(() -> {
                try {
                    for (int j = 0; j < 10; j++) {
                        int item = producerId * 10 + j;
                        queue.put(item); // 阻塞式添加
                        System.out.println("生产者" + producerId + "生产: " + item);
                        Thread.sleep(100); // 模拟生产时间
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    latch.countDown();
                }
            });
        }
        
        // 消费者线程
        for (int i = 0; i < consumerCount; i++) {
            final int consumerId = i;
            executorService.submit(() -> {
                try {
                    for (int j = 0; j < 15; j++) { // 每个消费者消费15个
                        Integer item = queue.take(); // 阻塞式获取
                        System.out.println("消费者" + consumerId + "消费: " + item);
                        Thread.sleep(150); // 模拟消费时间
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    latch.countDown();
                }
            });
        }
        
        try {
            latch.await();
            System.out.println("生产者消费者演示完成");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
    /**
     * 演示CompletableFuture异步编程
     */
    public void demonstrateCompletableFuture() {
        System.out.println("\n=== 演示CompletableFuture异步编程 ===");
        
        // 异步任务1
        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000);
                return "任务1完成";
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return "任务1被中断";
            }
        });
        
        // 异步任务2
        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(800);
                return "任务2完成";
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return "任务2被中断";
            }
        });
        
        // 组合两个异步任务
        CompletableFuture<String> combinedFuture = future1.thenCombine(future2, (result1, result2) -> {
            return result1 + " + " + result2;
        });
        
        try {
            String result = combinedFuture.get(3, TimeUnit.SECONDS);
            System.out.println("组合结果: " + result);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            System.err.println("异步任务执行异常: " + e.getMessage());
        }
    }
    
    /**
     * 演示线程池的使用
     */
    public void demonstrateThreadPool() {
        System.out.println("\n=== 演示线程池使用 ===");
        
        // 创建不同类型的线程池
        ExecutorService cachedPool = Executors.newCachedThreadPool();
        ExecutorService fixedPool = Executors.newFixedThreadPool(5);
        ExecutorService singlePool = Executors.newSingleThreadExecutor();
        
        System.out.println("提交任务到不同类型的线程池...");
        
        // 向缓存线程池提交任务
        for (int i = 0; i < 3; i++) {
            final int taskId = i;
            cachedPool.submit(() -> {
                System.out.println("缓存线程池执行任务: " + taskId);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }
        
        // 向固定大小线程池提交任务
        for (int i = 0; i < 3; i++) {
            final int taskId = i;
            fixedPool.submit(() -> {
                System.out.println("固定线程池执行任务: " + taskId);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }
        
        // 向单线程池提交任务
        for (int i = 0; i < 3; i++) {
            final int taskId = i;
            singlePool.submit(() -> {
                System.out.println("单线程池执行任务: " + taskId);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }
        
        // 关闭线程池
        cachedPool.shutdown();
        fixedPool.shutdown();
        singlePool.shutdown();
        
        System.out.println("所有线程池已关闭");
    }
    
    /**
     * 运行所有并发演示
     */
    public void runAllDemonstrations() {
        System.out.println("开始Java高并发编程演示...\n");
        
        demonstrateThreadSafetyIssue();
        demonstrateSynchronizedSolution();
        demonstrateAtomicSolution();
        demonstrateReentrantLock();
        demonstrateProducerConsumer();
        demonstrateCompletableFuture();
        demonstrateThreadPool();
        
        System.out.println("\n所有并发演示完成！");
    }
    
    /**
     * 关闭资源
     */
    public void shutdown() {
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(5, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}