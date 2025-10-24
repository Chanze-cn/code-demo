# Java 学习代码库

> 这是一个用于学习Java编程语言和相关框架的代码演示项目，包含各种编程概念、设计模式和框架使用的示例代码。

## 📚 项目简介

本项目旨在通过实际的代码示例来学习和掌握Java编程的各个方面，包括：
- 基础Java语法和面向对象编程
- Java并发编程（管程、线程同步等）
- 常用设计模式
- 主流框架的使用
- 测试驱动开发(TDD)
- 项目构建和依赖管理

## 🛠️ 技术栈

- **Java版本**: 8
- **构建工具**: Maven 3.x
- **测试框架**: JUnit 5 + AssertJ + Mockito
- **编码格式**: UTF-8

## 📁 项目结构

```
code-demo/
├── pom.xml                          # 父级Maven配置文件
├── README.md                        # 项目说明文档
├── java-concurrency/                # Java并发编程模块
│   ├── pom.xml                      # 子模块Maven配置
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   │   └── cn/
│   │   │   │       └── chanze/
│   │   │   │           ├── Main.java             # 主程序入口
│   │   │   │           └── monitor/
│   │   │   │               └── BlockQueue.java   # 阻塞队列实现（管程示例）
│   │   │   └── resources/                        # 资源文件目录
│   │   └── test/
│   │       ├── java/
│   │       │   └── cn/
│   │       │       └── chanze/
│   │       │           └── monitor/
│   │       │               └── BlockQueueTest.java # BlockQueue测试类
│   │       └── resources/                        # 测试资源文件
│   └── target/                                   # 编译输出目录
└── src/                                          # 根模块源码（预留）
    └── test/
        └── java/
            └── cn/
                └── chanze/
                    └── codedemo/
```

## 🚀 快速开始

### 环境要求

- JDK 8 或更高版本
- Maven 3.6 或更高版本
- Git (用于版本控制)

### 克隆项目

```bash
git clone <repository-url>
cd code-demo
```

### 编译项目

```bash
# 编译所有模块
mvn clean compile

# 编译特定模块
cd java-concurrency
mvn clean compile
```

### 运行测试

```bash
# 运行所有模块的测试
mvn test

# 运行特定模块的测试
cd java-concurrency
mvn test
```

### 运行应用程序

```bash
# 运行并发编程示例
cd java-concurrency
mvn exec:java -Dexec.mainClass="cn.chanze.Main"
```

## 📖 学习内容

### 当前已实现的功能

#### 1. Java并发编程 - 管程(Monitor)示例
- **BlockQueue类**: 基于ReentrantLock和Condition实现的阻塞队列
  - 使用管程模式解决生产者-消费者问题
  - 支持多线程安全的入队(enqueue)和出队(dequeue)操作
  - 队列满时入队操作会阻塞等待
  - 队列空时出队操作会阻塞等待
  - 使用Condition条件变量实现精确的线程通知机制

#### 2. 单元测试
- **JUnit 5测试**: 演示测试驱动开发
  - BlockQueueTest类测试多线程并发场景
  - 生产者-消费者模式的多线程测试
  - 线程安全性和同步机制验证

#### 3. 项目构建和依赖管理
- **Maven多模块项目**: 支持模块化开发
  - 父级pom.xml管理公共依赖和插件
  - java-concurrency子模块专注于并发编程学习
  - 集成JUnit 5、AssertJ、Mockito等测试框架

### 计划学习的内容

#### 设计模式
- [ ] 单例模式 (Singleton)
- [ ] 工厂模式 (Factory)
- [ ] 观察者模式 (Observer)
- [ ] 策略模式 (Strategy)
- [ ] 装饰器模式 (Decorator)
- [ ] 建造者模式 (Builder)

#### 集合框架
- [ ] List接口及其实现类
- [ ] Set接口及其实现类
- [ ] Map接口及其实现类
- [ ] 流式处理 (Stream API)

#### 并发编程
- [x] 管程 (Monitor) - BlockQueue实现
- [ ] 线程基础
- [ ] 同步机制 (synchronized, Lock)
- [ ] 线程池 (ThreadPoolExecutor)
- [ ] 原子操作 (Atomic类)
- [ ] 并发集合 (ConcurrentHashMap等)
- [ ] CompletableFuture
- [ ] Fork/Join框架
- [x] 生产者消费者模式 - BlockQueue示例
- [ ] 读写锁 (ReadWriteLock)
- [ ] 信号量 (Semaphore)
- [ ] 倒计时器 (CountDownLatch)
- [ ] 循环屏障 (CyclicBarrier)

#### 框架学习

##### Web框架
- [ ] Spring Framework基础
- [ ] Spring Boot
- [ ] Spring MVC
- [ ] Spring Security
- [ ] Spring Data JPA

##### 数据访问框架
- [ ] MyBatis
- [ ] MyBatis-Plus
- [ ] Hibernate
- [ ] JPA (Java Persistence API)

##### 缓存框架
- [ ] Redis集成
- [ ] Caffeine
- [ ] EhCache

##### 消息队列
- [ ] RabbitMQ
- [ ] Apache Kafka
- [ ] RocketMQ

##### 微服务框架
- [ ] Spring Cloud
- [ ] Dubbo
- [ ] Nacos
- [ ] Gateway

##### 工具框架
- [ ] Lombok
- [ ] MapStruct
- [ ] Jackson
- [ ] Gson
- [ ] Apache Commons

##### 测试框架
- [ ] Mockito
- [ ] TestContainers
- [ ] WireMock
- [ ] AssertJ

#### 其他主题
- [ ] 注解 (Annotations)
- [ ] 反射 (Reflection)
- [ ] 泛型 (Generics)
- [ ] Lambda表达式
- [ ] 函数式编程

## 🔍 核心实现解析

### BlockQueue - 管程模式实现

BlockQueue是一个基于Java管程(Monitor)模式实现的线程安全阻塞队列，展示了并发编程中经典的同步机制。

#### 核心特性
- **线程安全**: 使用ReentrantLock确保操作的原子性
- **阻塞等待**: 队列满时入队阻塞，队列空时出队阻塞
- **精确通知**: 使用Condition条件变量实现精确的线程唤醒
- **生产者-消费者模式**: 支持多生产者多消费者并发场景

#### 技术实现要点

```java
// 核心同步机制
private final Lock lock = new ReentrantLock();
private final Condition notFull = lock.newCondition();   // 队列不满条件
private final Condition notEmpty = lock.newCondition();  // 队列不空条件

// 入队操作 - 队列满时阻塞
public void enqueue(T t) throws InterruptedException {
    lock.lock();
    try {
        while (queue.size() >= capacity) {
            notFull.await();  // 等待队列不满
        }
        queue.add(t);
        notEmpty.signalAll();  // 通知等待出队的线程
    } finally {
        lock.unlock();
    }
}

// 出队操作 - 队列空时阻塞
public T dequeue() throws InterruptedException {
    lock.lock();
    try {
        while (queue.isEmpty()) {
            notEmpty.await();  // 等待队列不空
        }
        T result = queue.remove(0);
        notFull.signalAll();   // 通知等待入队的线程
        return result;
    } finally {
        lock.unlock();
    }
}
```

#### 学习价值
- **管程概念**: 理解管程作为高级同步原语的优势
- **条件变量**: 掌握Condition的使用场景和最佳实践
- **死锁避免**: 学习如何避免死锁和活锁问题
- **性能优化**: 理解锁的粒度和条件通知的优化策略

## 🧪 测试说明

项目使用JUnit 5作为测试框架，所有测试类都以`Test`结尾。

### 运行特定测试

```bash
# 运行所有测试
mvn test

# 运行特定模块的测试
cd java-concurrency
mvn test

# 运行特定测试类
mvn test -Dtest=BlockQueueTest

# 运行特定测试方法
mvn test -Dtest=BlockQueueTest#testMultiThreadEnqueueAndDequeue
```

### 测试覆盖率

```bash
# 生成测试覆盖率报告
mvn jacoco:report
```

## 📝 代码规范

### 命名规范
- 类名使用大驼峰命名法 (PascalCase)
- 方法名和变量名使用小驼峰命名法 (camelCase)
- 常量使用全大写下划线分隔 (UPPER_SNAKE_CASE)
- 包名使用小写字母和点分隔

### 注释规范
- 类和方法必须添加JavaDoc注释
- 复杂逻辑需要添加行内注释
- 使用中文注释，便于理解

### 代码格式
- 使用4个空格缩进
- 每行代码不超过120个字符
- 大括号使用K&R风格

## 🔧 开发工具推荐

### IDE
- IntelliJ IDEA (推荐)
- Eclipse
- VS Code

### 插件推荐
- Maven Helper
- SonarLint
- CheckStyle

## 📚 学习资源

### 官方文档
- [Java官方文档](https://docs.oracle.com/en/java/)
- [Maven官方文档](https://maven.apache.org/guides/)
- [JUnit 5官方文档](https://junit.org/junit5/docs/current/user-guide/)

### 推荐书籍
- 《Java核心技术》
- 《Effective Java》
- 《Java并发编程实战》
- 《Spring实战》

### 在线资源
- [菜鸟教程 - Java](https://www.runoob.com/java/java-tutorial.html)
- [廖雪峰Java教程](https://www.liaoxuefeng.com/wiki/1252599548343744)
- [Baeldung Java教程](https://www.baeldung.com/)

## 🤝 贡献指南

欢迎提交Issue和Pull Request来帮助完善这个学习项目！

### 提交规范
- 使用清晰的中文描述
- 包含具体的代码示例
- 添加相应的测试用例

### 分支策略
- `main`: 主分支，包含稳定的代码
- `feature/*`: 功能分支，用于开发新功能
- `bugfix/*`: 修复分支，用于修复bug

## 📄 许可证

本项目仅用于学习目的，采用MIT许可证。

## 📞 联系方式

如有问题或建议，欢迎通过以下方式联系：
- 邮箱: [781830133@qq.com]
- GitHub: [https://github.com/chanze2013]

---

**Happy Coding! 🎉**

> 记住：编程是一门实践的艺术，多写代码，多思考，多总结！