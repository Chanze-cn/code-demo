# Kafka 核心组件

## 简介

Kafka 是一个分布式流处理平台，主要用于构建实时数据管道和流式应用程序。它具有高吞吐量、低延迟、高扩展性等特点，被广泛应用于日志收集、消息传递、事件流处理等场景。

**Kafka 的核心组件包括：**
- **Producer**：负责生产消息
- **Consumer**：负责消费消息
- **Consumer Group**：消费者组，管理多个消费者
- **Topic**：消息主题，逻辑上的消息分类
- **Broker**：Kafka 服务器节点，负责存储消息
- **Partition**：分区，Topic 的物理存储单元
- **Offset**：消息偏移量，标识消息在分区中的位置

## 核心概念

### 1. Producer（生产者）

Producer 是负责生产消息的实体，将消息发送到 Kafka 集群，并投递到指定的 Topic。

**核心职责：**
- 将业务数据封装成消息并发送到 Kafka
- 支持同步/异步发送模式
- 支持消息分区策略（基于 key 或自定义分区器）
- 支持消息压缩和批量发送以提升性能

### 2. Consumer（消费者）

Consumer 是负责消费消息的实体，从 Kafka 中订阅指定 Topic 的消息。Kafka 根据 Consumer Group 的配置，将消息推送给对应的 Consumer。

**核心职责：**
- 订阅一个或多个 Topic
- 从分区中拉取消息进行处理
- 提交消费位移（Offset）以记录消费进度
- 支持手动或自动提交位移

### 3. Consumer Group（消费者组）

多个 Consumer 组成一个 Consumer Group，同一个消息只能被同一个 Consumer Group 中的一个 Consumer 消费。这种设计既保证了消费者的可扩展性，又避免了消息被重复消费。

**核心特性：**

- **横向扩展能力**
  - Consumer Group 可以包含多个 Consumer
  - 当消费速度跟不上生产速度时，可通过增加 Consumer 数量来提高消费能力
  - 实现系统的横向扩展，**提高系统吞吐量和可扩展性**

- **避免重复消费**
  - 同一个消息只会被 Consumer Group 中的其中一个 Consumer 消费
  - 即便有多个 Consumer，同一个消息也只会被消费一次
  - **避免消息重复消费**，保证消息处理的幂等性

### 4. Topic（主题）

Topic 是 Kafka 中的一个逻辑概念，相当于消息的分类。一个 Topic 可以有多个 Partition，一个 Partition 可以有多个 Replica。

**核心特性：**
- Topic 是消息主题，也是 Consumer Group 的消费目标
- 同一个 Topic 可以切分成多个分区（Partition），Producer 可以通过一定的策略将消息投递到不同的 Partition 中
- 每个 Partition 中的消息保持有序
- Consumer 消费哪个分区的消息，由 Consumer Group 的分区分配策略决定（可灵活配置）

**分区分配策略：**

#### Range Assignment Strategy（范围分配策略）

**分配逻辑：** 按分区序号范围平均分配给消费者。

**分配步骤：**
1. 先将主题的所有分区按序号排序（例如：主题 T0、T1；T0 有 1 个分区，T1 有 2 个分区，则分区排序为 T0P0、T1P0、T1P1）
2. 计算每个消费者平均分配的分区数：`总分区数 / 消费者数量`
3. 余数部分由前 N 个消费者多分配 1 个分区（N = 总分区数 % 消费者数量）

**优缺点：**
- ✅ **优点**：实现简单
- ❌ **缺点**：可能导致消费者负载不均（当分区数不能被消费者数量整除时，前几个消费者会多分配分区）

#### RoundRobin Assignment Strategy（轮询分配策略）

**分配逻辑：** 将所有分区和消费者按名称排序，然后按"轮询"方式分配给消费者。

**分配步骤：**
1. 对所有分区（按主题+分区序号）和消费者（按 ID）排序
   - 例如：两个消费者 C0、C1，两个主题 T0、T1，T0 有 1 个分区，T1 有 2 个分区
   - 分区排序：T0P0、T1P0、T1P1
   - 消费者排序：C0、C1
2. 按顺序依次将每个分区分配给下一个消费者（循环往复）
   - 结果：C0 分配到 T0P0，C1 分配到 T1P0，C0 分配到 T1P1

**优缺点：**
- ✅ **优点**：分配更均衡，适合多主题场景（避免某一个消费者集中分配到某一主题的多个分区）
- ❌ **缺点**：如果消费者骤减时，会造成资源分配不均衡，导致部分 Consumer 负载过重

#### Sticky Assignment Strategy（粘性分配策略）

**分配逻辑：** 在保证均衡性的前提下，尽可能保留原有分配关系，避免频繁"搬家"导致 Consumer 重置状态。

**分配步骤：**
1. 先尝试在旧的分配结果基础上调整（如仅对新增/下线消费者对应的分区重分配）
2. 若仍不均衡，再对剩余分区做最小改动的均衡调整

**优缺点：**
- ✅ **优点**：消费者重平衡时状态迁移最少，减少 Seek/缓存命中损耗
- ❌ **缺点**：实现复杂，且在极端规模变化场景下仍需完整重分配

### 5. Broker（代理节点）

Broker 是 Kafka 中的一个物理概念，相当于一个消息的存储节点。一个 Kafka 集群可以有多个 Broker，一个 Broker 可以存储多个 Partition。

**核心职责：**
- 维护 Partition 的 Leader/Follower 角色，并与 Controller 协商副本状态
- 处理 Producer/Consumer 的网络请求，提供批量写入、批量拉取能力
- 通过 `ReplicaFetcher` 线程同步 Leader 日志，维持 ISR（In-Sync Replica）集合
- 暴露指标供监控系统分析磁盘、网络、压缩等瓶颈

### 6. Partition（分区）

Partition 是 Kafka 中的一个物理概念，相当于消息的存储单元。一个 Topic 可以有多个 Partition，一个 Partition 可以有多个 Replica。

**内部结构：**
- 日志文件按 `segment` 切分，每段由 `.log` 数据文件与 `.index` 偏移索引组成，便于快速截断与清理
- Leader Partition 负责写入顺序保证，Follower 只做顺序复制
- 生产者可通过自定义分区器（如基于 key 的 hash）将同一业务实体路由到同一分区，保持局部有序
- 清理策略（Log Compaction / Log Deletion）以分区为单位执行，与保留时间和最大容量相关

### 7. Replica（副本）

Replica 是 Kafka 中的一个物理概念，相当于消息的备份。一个 Partition 可以有多个 Replica，一个 Replica 只能存储在一个 Broker 上。

**副本类型：**

- **Leader Replica（主副本）**
  - 对外提供读写服务
  - 维护最新的高水位（HW，High Watermark）

- **Follower Replica（从副本）**
  - 通过拉取 Leader 日志保持同步
  - 追上 HW 后进入 ISR（In-Sync Replica）集合

**关键机制：**
- 同步副本集合（ISR）决定了 `acks=all` 下的提交安全性
- ISR 越小写入延迟越低，但可靠性降低
- `unclean.leader.election.enable=false` 可避免非 ISR 副本被推举为 Leader，防止数据回退

### 8. Offset（偏移量）

Offset 是 Kafka 中的一个概念，标识消息在 Partition 中的位置。一个 Partition 可以有多个 Offset，每个 Offset 对应一条消息。

**核心机制：**
- Consumer 提交位移一般写入 `__consumer_offsets` 内部主题，可选自动或手动提交
- `committed offset` 代表已消费且业务确认的最后位置
- `current position` 代表下一条待拉取消息的位置
- 通过 `seek` 可以将消费位点移动到任意 Offset，常用于补偿消息或跳过脏数据
- 控制 Offset 与业务事务一致性时，可借助幂等生产者 + 事务性写入（exactly-once）保持端到端语义

### 9. Controller（控制器）

Controller 是集群中的特殊 Broker，由 ZooKeeper（或 KRaft 模式下的 Quorum Controller）选举产生。

**核心职责：**
- 监听 Broker 上下线事件并触发分区重分配
- 维护元数据（主题、分区、副本状态），并广播给其他 Broker
- 协调副本选举：当 Leader 异常时，按优先级从 ISR 中选择新的 Leader
- 与 AdminClient 交互，处理创建/删除主题、增加分区等管理操作

### 10. 存储与可靠性

**存储优化：**
- 日志顺序追加 + Page Cache 提升磁盘吞吐
- 结合批量压缩（gzip、snappy、lz4、zstd）降低网络成本

**可靠性保障：**
- `acks` 配置控制写入确认：
  - `0`：最快但不可靠（不等待确认）
  - `1`：只确认 Leader 写入成功
  - `all`：需 ISR 全部确认（最高可靠性）
- 幂等生产者（`enable.idempotence=true`）通过 Producer ID + 序列号去重，避免重复写
- 事务性生产者配合消费位移提交，可实现跨 Topic、跨分区的精准一次（exactly-once）处理

### 11. 协调组件

**ZooKeeper（传统架构）**
- 存放集群元数据、ACL、配额等信息
- 参与 Controller 选举
- 注：Kafka 2.8+ 支持 KRaft 模式，可脱离 ZooKeeper

**Group Coordinator（组协调器）**
- 每个 Consumer Group 对应的 Broker 角色
- 负责成员管理、再平衡和 Offset 提交

**Transaction Coordinator（事务协调器）**
- 为事务性 Producer 维护事务日志
- 保障 commit/abort 语义的一致性