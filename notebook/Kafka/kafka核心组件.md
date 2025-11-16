# Kafka核心组件

## 简介

kafka是一个分布式流处理平台，主要用于构建实时数据管道和流式应用程序。它具有高吞吐量、低延迟、高扩展性等特点，被广泛应用于日志收集、消息传递、事件流处理等场景。
kafka的核心组件包括：
- Producer：负责生产消息
- Consumer：负责消费消息
- Consumer Group：负责消费消息的组
- Topic：负责存储消息
- Broker：负责存储消息
- Partition：负责存储消息的分区
- Offset：负责存储消息的偏移量


## 核心概念

### 1. Producer

负责生产消息的实体，把消息发送到Kafka集群。投递消息到某个指定的Topic。

### 2. Consumer

负责消费消息的实体，从Kafka中订阅某个Topic的消息，然后Kafka在收到对应Topic的消息后，会根据Consumer Group的配置，将消息推送给对应的Consumer。

### 3. Consumer Group

多个Consumer组成一个Consumer Group，同一个消息只能被同一个Consumer Group中的一个Consumer消费。这可以保证消费者的可扩展性同时又避免消息被重复消费。

- Consumer Group可以有包含多个Consumer，保障了如果消费消息的速度跟不上生产消息的速度，可以通过增加Consumer的数量来提高消费能力。实现横向扩展。**提高系统吞吐量，提高可扩展性**
- 同一个消息，只会被Consumer Group中的其中一个Consumer消费。保证了即便有多个Consumer，同一个消息也只会被消费一次。避免重复消费。**避免消息重复消费**

### 4. Topic

Topic是Kafka中的一个逻辑概念，相当于一个消息的分类。一个Topic可以有多个Partition，一个Partition可以有多个Replica。
- Topic是一个消息主题，也是Consumer Group的消费目标。
- 对于同一个Topic，可以切分成多个分区Partition，这样Kafka在产生该Topic的消息时，可以通过一定的策略投递到不同的Partition中。
- 每个Partition中的消息是保持有序的。
- 每个Consumer消费哪个分区的消息，是由Consumer Group分配策略决定的（可灵活配置）。
  - 默认情况下，Kafka会采用Range Assignment Strategy（范围分配策略）;
    - 逻辑：按分区序号范围平均分配给消费者。
    - 步骤：
      1. 先将主题的所有分区按序号排序（如主题排序：T0、T1；T0有1个分区，T1有2个分区，则分区排序：T0P0、T1P0、T1P1）；
      2. 计算每个消费者平均分配的分区数：总分区数/消费者数量。余数部分由前N个消费者个多分配1个分区（N=总分区数 % 消费者数量）
    - 优点：实现简单；
    - 缺点：但可能导致消费者负载不均（当分区数不能被消费者数量整除时，前几个消费者会多分配分区。）
  - RoundRobin Assignment Strategy（轮询分配策略）;
    - 逻辑：将所有分区和消费者按名称排序，然后按“轮询”方式分配给消费者。
    - 步骤：
      1. 对所有分区（按主题+分区序号）和消费者（按ID）排序。如两个消费者C0、C1，两个主题T0、T1，T0有1个分区，T1有2个分区，则分区排序：T0P0、T1P0、T1P1，消费者排序：C0、C1。
      2. 按顺序一次将每个分区分配给下一个消费者（循环往复）。则C0分配到T0P0，C1分配到T1P0，C0分配到T1P1
    - 优点：分配更均衡，适合多主题场景（避免某一个消费者集中分配到某一主题的多个分区）；
    - 缺点：如果消费者骤减时，则会造成资源分配不均衡，比如循环完之后的那些主题都集中在几个Consumer上，则会导致那几个比较重负载。
  - Sticky Assignment Strategy（粘性分配策略）;
    - 逻辑：在保证均衡性的前提下，尽可能保留原有分配关系，避免频繁“搬家”导致 Consumer 重置状态。
    - 步骤：
      1. 先尝试在旧的分配结果基础上调整（如仅对新增/下线消费者对应的分区重分配）；
      2. 若仍不均衡，再对剩余分区做最小改动的均衡调整。
    - 优点：消费者重平衡时状态迁移最少，减少 Seek/缓存命中损耗；
    - 缺点：实现复杂，且在极端规模变化场景下仍需完整重分配。

### 5. Broker

Broker是Kafka中的一个物理概念，相当于一个消息的存储节点。一个Kafka集群可以有多个Broker，一个Broker可以有多个Partition。Broker除了承载日志文件，还负责：
- 维护 Partition 的 Leader/Follower 角色，并与 Controller 协商副本状态；
- 处理 Producer/Consumer 的网络请求，提供批量写入、批量拉取能力；
- 通过 `ReplicaFetcher` 线程同步 Leader 日志，维持 ISR（in-sync replica）集合；
- 暴露指标供监控系统分析磁盘、网络、压缩等瓶颈。

### 6. Partition

Partition是Kafka中的一个物理概念，相当于一个消息的存储单元。一个Topic可以有多个Partition，一个Partition可以有多个Replica。Partition内部结构：
- 日志文件按 `segment` 切分，每段由 `.log` 数据文件与 `.index` 偏移索引组成，便于快速截断与清理；
- Leader Partition 负责写入顺序保证，Follower 只做顺序复制；
- 生产者可通过自定义分区器（如基于 key 的 hash）将同一业务实体路由到同一分区，保持局部有序；
- 清理策略（Log Compaction / Log Deletion）以分区为单位执行，与保留时间和最大容量相关。

### 7. Replica

Replica是Kafka中的一个物理概念，相当于一个消息的备份。一个Partition可以有多个Replica，一个Replica可以有多个Broker。Replica主要分为：
- Leader Replica：对外提供读写服务，维护最新的高水位（HW）；
- Follower Replica：通过拉取 Leader 日志保持同步，追上 HW 后进入 ISR；
- 同步副本集合（ISR）决定了 `acks=all` 下的提交安全性，ISR 越小写入延迟越低但可靠性降低；
- `unclean.leader.election.enable=false` 可避免非 ISR 副本被推举为 Leader，防止数据回退。

### 8. Offset

Offset是Kafka中的一个概念，相当于一个消息的偏移量。一个Partition可以有多个Offset，一个Offset可以有多个消息。
- Consumer 提交位移一般写入 `__consumer_offsets` 内部主题，可选自动或手动提交；
- `committed offset` 代表已消费且业务确认的最后位置，`current position` 代表下一条待拉取消息；
- 通过 `seek` 可以将消费位点移动到任意 Offset，常用于补偿消息或跳过脏数据；
- 控制 Offset 与业务事务一致性时，可借助幂等生产者 + 事务性写入（exactly-once）保持端到端语义。

### 9. Controller

Controller 是集群中的特殊 Broker，由 ZooKeeper（或 KRaft 模式下的 Quorum Controller）选举产生，负责：
- 监听 Broker 上下线事件并触发分区重分配；
- 维护元数据（主题、分区、副本状态），并广播给其他 Broker；
- 协调副本选举：当 Leader 异常时，按优先级从 ISR 中选择新的 Leader；
- 与 AdminClient 交互，处理创建/删除主题、增加分区等管理操作。

### 10. 存储与可靠性

- 日志顺序追加 + Page Cache 提升磁盘吞吐，结合批量压缩（gzip、snappy、lz4、zstd）降低网络成本；
- `acks` 配置控制写入确认：`0` 最快但不可靠，`1` 只确认 Leader，`all` 需 ISR 全部确认；
- 幂等生产者（`enable.idempotence=true`）通过 Producer ID + 序列号去重，避免重复写；
- 事务性生产者配合消费位移提交，可实现跨 Topic、跨分区的精准一次（exactly-once）处理。

### 11. 协调组件

- ZooKeeper：在传统架构中存放集群元数据、ACL、配额等信息，并参与 Controller 选举；
- Group Coordinator：每个 Consumer Group 对应的 Broker 角色，负责成员管理、再平衡和 Offset 提交；
- Transaction Coordinator：为事务性 Producer 维护事务日志，保障 commit/abort 语义。