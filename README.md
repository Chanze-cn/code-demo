# Java 学习代码库

> 这是一个用于学习Java编程语言和相关框架的代码演示项目，包含各种编程概念、设计模式和框架使用的示例代码。

## 📚 项目简介

本项目旨在通过实际的代码示例来学习和掌握Java编程的各个方面，包括：
- 基础Java语法和面向对象编程
- 常用设计模式
- 主流框架的使用
- 测试驱动开发(TDD)
- 项目构建和依赖管理

## 🛠️ 技术栈

- **Java版本**: 8 (兼容JDK 8+)
- **构建工具**: Maven 3.x
- **测试框架**: JUnit 5
- **编码格式**: UTF-8

## 📁 项目结构

```
code-demo/
├── pom.xml                          # Maven配置文件
├── README.md                        # 项目说明文档
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── cn/
│   │   │       └── chanze/
│   │   │           └── codedemo/
│   │   │               ├── App.java              # 主应用程序类
│   │   │               └── Calculator.java       # 计算器示例类
│   │   └── resources/                            # 资源文件目录
│   └── test/
│       ├── java/
│       │   └── cn/
│       │       └── chanze/
│       │           └── codedemo/
│       │               ├── AppTest.java          # App类测试
│       │               └── CalculatorTest.java   # Calculator类测试
│       └── resources/                            # 测试资源文件
└── target/                                       # 编译输出目录
```

## 🚀 快速开始

### 环境要求

- JDK 8 或更高版本 (当前项目使用JDK 8)
- Maven 3.6 或更高版本
- Git (用于版本控制)

### 克隆项目

```bash
git clone <repository-url>
cd code-demo
```

### 编译项目

```bash
mvn clean compile
```

### 运行测试

```bash
mvn test
```

### 运行应用程序

```bash
mvn exec:java -Dexec.mainClass="cn.chanze.codedemo.App"
```

或者先打包再运行：

```bash
mvn clean package
java -jar target/code-demo-1.0.0.jar
```

## 📖 学习内容

### 当前已实现的功能

#### 1. 基础Java语法
- **Calculator类**: 演示基本的数学运算和异常处理
  - 加法、减法、乘法、除法运算
  - 除零异常处理
  - 方法重载和参数验证

#### 2. 单元测试
- **JUnit 5测试**: 演示测试驱动开发
  - 正常情况测试
  - 边界条件测试
  - 异常情况测试
  - 测试生命周期管理

#### 3. Java高并发编程
- **ConcurrencyDemo类**: 演示多线程和并发编程基础
  - 线程创建和管理
  - synchronized关键字使用
  - 线程安全问题演示
  - 原子操作类使用
  - 并发集合使用
  - 线程池使用

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
- [ ] 线程基础
- [ ] 同步机制 (synchronized, Lock)
- [ ] 线程池 (ThreadPoolExecutor)
- [ ] 原子操作 (Atomic类)
- [ ] 并发集合 (ConcurrentHashMap等)
- [ ] CompletableFuture
- [ ] Fork/Join框架
- [ ] 生产者消费者模式
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

## 🧪 测试说明

项目使用JUnit 5作为测试框架，所有测试类都以`Test`结尾。

### 运行特定测试

```bash
# 运行所有测试
mvn test

# 运行特定测试类
mvn test -Dtest=CalculatorTest

# 运行特定测试方法
mvn test -Dtest=CalculatorTest#testAdd
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