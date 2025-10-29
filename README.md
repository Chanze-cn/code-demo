# Code Demo 学习代码库

> 这是一个综合性的技术学习代码库，包含Java编程实践、MySQL数据库原理等技术学习和笔记。

## 📚 项目简介

本项目旨在通过实际的代码示例和详细的学习笔记来深入理解各种技术原理，包括：

### 代码实践模块
- **Java并发编程**：管程、线程同步、生产者-消费者模式等
- 基础Java语法和面向对象编程
- 常用设计模式
- 主流框架的使用
- 测试驱动开发(TDD)
- 项目构建和依赖管理

### 学习笔记模块
- **Java并发编程笔记**：管程原理与实践
- **MySQL数据库笔记**：事务隔离、日志体系等核心原理
- 技术原理的深入理解和总结

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
├── notebook/                        # 学习笔记目录
│   ├── java-concurrency/            # Java并发编程笔记
│   │   └── 管程.md                  # 管程原理与实践
│   ├── mysql/                       # MySQL数据库笔记
│   │   ├── 事务隔离/                # 事务隔离笔记
│   │   │   ├── 事务隔离.md
│   │   │   └── image/              # 相关图片资源
│   │   └── MySQL日志体系/           # MySQL日志体系笔记
│   │       ├── mysql日志体系.md
│   │       └── image/              # 相关图片资源
│   └── image/                       # 笔记图片资源
└── src/                             # 根模块源码（预留）
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

### Java并发编程

#### 代码实践
- [x] **管程 (Monitor)** - BlockQueue实现
  - [x] 基于ReentrantLock和Condition实现的阻塞队列
  - [x] 使用管程模式解决生产者-消费者问题
  - [x] 支持多线程安全的入队(enqueue)和出队(dequeue)操作
  - [x] 队列满时入队操作会阻塞等待
  - [x] 队列空时出队操作会阻塞等待
  - [x] 使用Condition条件变量实现精确的线程通知机制
- [ ] 线程基础
- [ ] 同步机制 (synchronized, Lock)
- [ ] 线程池 (ThreadPoolExecutor)
- [ ] 原子操作 (Atomic类)
- [ ] 并发集合 (ConcurrentHashMap等)
- [ ] CompletableFuture
- [ ] Fork/Join框架
- [ ] 读写锁 (ReadWriteLock)
- [ ] 信号量 (Semaphore)
- [ ] 倒计时器 (CountDownLatch)
- [ ] 循环屏障 (CyclicBarrier)

#### 学习笔记
- [x] **管程原理与实践** (`notebook/java-concurrency/管程.md`)
  - [x] MESA管程模型详解
  - [x] Hasen、Hoare、MESA三种管程模型的对比
  - [x] 管程的实现方式和优势

### MySQL数据库原理

#### 事务隔离 (`notebook/mysql/事务隔离/事务隔离.md`)
- [x] **ACID特性**：原子性、一致性、隔离性、持久性的深入理解
- [x] **事务隔离级别**：四种隔离级别的详细对比和实际效果
- [x] **MVCC机制**：多版本并发控制的实现原理
- [x] **视图机制**：不同隔离级别下的视图创建时机
- [x] **长事务风险**：长事务的危害和避免方法
- [ ] 锁机制详解
- [ ] 死锁检测与处理

#### MySQL日志体系 (`notebook/mysql/MySQL日志体系/mysql日志体系.md`)
- [x] **redo log（重做日志）**
  - [x] InnoDB引擎层的物理日志
  - [x] WAL技术（Write-Ahead Logging）
  - [x] crash-safe能力实现机制
  - [x] 循环写入的日志结构
- [x] **binlog（二进制日志）**
  - [x] Server层的逻辑日志
  - [x] 数据恢复和主从复制
- [x] **两阶段提交（2PC）**
  - [x] redo log和binlog的一致性保证
  - [x] 崩溃恢复时的处理机制
  - [x] 数据一致性原理
- [x] undo log详解
- [ ] 慢查询日志
- [ ] 错误日志

#### MySQL索引原理 (`notebook/mysql/MySQL索引原理/MySQL索引原理.md`)
- [ ] MySQL索引基础
  - [ ] 索引的基本概念与作用
  - [ ] 索引分类（主键索引、唯一索引、普通索引、全文索引、联合索引）
  - [ ] 单列索引与多列索引的区别
- [ ] InnoDB存储引擎下的索引结构
  - [ ] B+树索引原理
  - [ ] 聚集索引（Clustered Index）
  - [ ] 二级索引（辅助索引/Secondary Index）
  - [ ] 覆盖索引（Covering Index）
  - [ ] 回表与索引下推
  - [ ] 唯一索引与主键索引的区别
- [ ] 索引设计与优化实践
  - [ ] 最左前缀原则
  - [ ] 索引失效的常见原因
  - [ ] 如何选择合适的索引列
  - [ ] 联合索引顺序对查询的影响
  - [ ] 减少冗余和重复索引的方法
  - [ ] 使用EXPLAIN分析SQL执行计划
- [ ] 高频面试/实际问题
  - [ ] 什么情况下索引不会被用到
  - [ ] LIKE、范围查询对索引的影响
  - [ ] NULL值对索引的影响
  - [ ] 覆盖索引和回表的实际案例
- [ ] 特殊索引类型
  - [ ] 前缀索引（Prefix Index）
  - [ ] 全文索引（Fulltext Index）
  - [ ] 哈希索引（Memory引擎）
  - [ ] 空间索引（GIS/Spatial Index）
- [ ] 索引的维护与监控
  - [ ] 索引的重建与优化
  - [ ] 如何判断索引是否被使用
  - [ ] show index/statistics监控与分析
- [ ] 索引相关底层细节
  - [ ] B+树与B树的区别
  - [ ] 索引页分裂、合并与碎片整理
  - [ ] 页面结构：Page、Slot、Record
  - [ ] 索引与事务的关系
  - [ ] 行锁、间隙锁与索引的关系



### 设计模式

- [ ] 单例模式 (Singleton)
- [ ] 工厂模式 (Factory)
- [ ] 观察者模式 (Observer)
- [ ] 策略模式 (Strategy)
- [ ] 装饰器模式 (Decorator)
- [ ] 建造者模式 (Builder)
- [ ] 适配器模式 (Adapter)
- [ ] 代理模式 (Proxy)
- [ ] 模板方法模式 (Template Method)

### 集合框架

- [ ] List接口及其实现类
- [ ] Set接口及其实现类
- [ ] Map接口及其实现类
- [ ] 流式处理 (Stream API)
- [ ] Optional使用
- [ ] 集合性能优化

### 框架学习

#### Web框架
- [ ] Spring Framework基础
- [ ] Spring Boot
- [ ] Spring MVC
- [ ] Spring Security
- [ ] Spring Data JPA

#### 数据访问框架
- [ ] MyBatis
- [ ] MyBatis-Plus
- [ ] Hibernate
- [ ] JPA (Java Persistence API)

#### 缓存框架
- [ ] Redis集成
- [ ] Caffeine
- [ ] EhCache

#### 消息队列
- [ ] RabbitMQ
- [ ] Apache Kafka
- [ ] RocketMQ

#### 微服务框架
- [ ] Spring Cloud
- [ ] Dubbo
- [ ] Nacos
- [ ] Gateway

#### 工具框架
- [ ] Lombok
- [ ] MapStruct
- [ ] Jackson
- [ ] Gson
- [ ] Apache Commons

#### 测试框架
- [x] JUnit 5 - 演示测试驱动开发
  - [x] BlockQueueTest类测试多线程并发场景
  - [x] 生产者-消费者模式的多线程测试
  - [x] 线程安全性和同步机制验证
- [ ] Mockito
- [ ] TestContainers
- [ ] WireMock
- [ ] AssertJ

### 项目构建和依赖管理

- [x] **Maven多模块项目**
  - [x] 父级pom.xml管理公共依赖和插件
  - [x] java-concurrency子模块专注于并发编程学习
  - [x] 集成JUnit 5、AssertJ、Mockito等测试框架
- [ ] Gradle构建
- [ ] 持续集成/持续部署 (CI/CD)

### 其他主题

- [ ] 注解 (Annotations)
- [ ] 反射 (Reflection)
- [ ] 泛型 (Generics)
- [ ] Lambda表达式
- [ ] 函数式编程
- [ ] IO/NIO
- [ ] 网络编程
- [ ] JVM调优

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

## 📝 学习笔记

本项目的笔记位于 `notebook/` 目录下，包含以下内容：

### Java并发编程笔记
- **管程.md**：详细介绍了MESA管程模型，包括三种管程模型的对比和实际应用

### MySQL数据库笔记

#### 事务隔离
- 深入理解ACID特性
- 四种隔离级别的详细对比和实际效果示例
- MVCC（多版本并发控制）的实现原理
- 事务启动方式和长事务的注意事项

#### MySQL日志体系
- redo log和binlog的结构和作用
- 两阶段提交机制的原理和必要性
- 数据恢复机制的实践应用
- crash-safe能力的实现原理

所有笔记都包含详细的原理说明、实际示例和实践建议，适合深入学习相关技术原理。

## 📚 学习资源

### 官方文档
- [Java官方文档](https://docs.oracle.com/en/java/)
- [MySQL官方文档](https://dev.mysql.com/doc/)
- [Maven官方文档](https://maven.apache.org/guides/)
- [JUnit 5官方文档](https://junit.org/junit5/docs/current/user-guide/)

### 推荐书籍
- 《Java核心技术》
- 《Effective Java》
- 《Java并发编程实战》
- 《高性能MySQL》
- 《MySQL技术内幕：InnoDB存储引擎》
- 《Spring实战》

### 在线课程
- 极客时间《Java并发编程实战》
- 极客时间《MySQL实战45讲》

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