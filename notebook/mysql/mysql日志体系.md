# MySQL的重要日志文件
mysql的重要日志文件包括redolog日志文件和binlog日志文件。

# 重要的日志文件：redolog日志


redolog日志文件是InnoDB引擎内置的日志文件，属于引擎层的日志文件，当一条记录要更新的时候，InnoDB引擎会先写到redo log日志文件里面，并更新内存，这个时候就算更新完成了。同时，InnoDB会在适当的时候，将redo log日志文件中的内容刷新到磁盘文件中。redo log日志文件用于保证MySQL宕机后，数据可以恢复到宕机前的状态。

## redo log日志文件的结构

![redo log日志文件的结构](../image/mysql/mysql日志体系/redolog模型示意图.webp)

redo log日志文件是一个循环队列，会有两个指针，一个指向队头一个指向队尾，队头到队尾之间就是redo log的日志内容，当队尾指针等于队头-1 时，表示队列满了；当队尾指针等于队头时，表示队列空了。写redo log日志文件的时候，移动队尾指针，将redo log日志文件的内容写入硬盘时，移动队头指针。

## redo log日志文件的作用
redo log日志文件的作用是保证MySQL宕机后，数据可以恢复到宕机前的状态。当MySQL宕机后，InnoDB引擎会读取redo log日志文件中的内容，将数据恢复到宕机前的状态。
> 有了redo log日志文件，InnoDB引擎就可以保证即使数据库异常重启，之前提交的数据也不会丢失，这个能力成为crash-safe能力。整个过程用到的技术称之为WAL(Write-Ahead Logging)技术。即先写日志，再写磁盘.

# 重要的日志文件：binlog日志 todo

前面说redo log是InnoDB引擎内置的日志文件，属于引擎层的日志文件，而binlog是MySQL Server层实现的日志文件，属于Server层的日志文件。binlog日志文件用于记录数据库的变更操作，可以用于数据恢复和数据同步。

## binlog与redolog的对比
1. redolog是InnoDB引擎特有的日志文件；binlog是MySQL Server层实现的日志文件，所有的引擎都可以使用
2. redolog是物理日志，记录的是数据页的物理修改，而不是SQL语句；binlog是逻辑日志，记录的是语句的原始逻辑，如给ID=1这一行的字段加1，
3. redolog是循环写入，空间固定会用完；binlog是可以追加写入

## 执行器和InnoDB执行一条更新语句的过程

1. 执行器先找引擎取 ID=2 这一行。ID 是主键，引擎直接用树搜索找到这一行。如果 ID=2 这一行所在的数据页本来就在内存中，就直接返回给执行器；否则，需要先从磁盘读入内存，然后再返回。
2. 执行器拿到引擎给的行数据，把这个值加上 1，比如原来是 N，现在就是 N+1，得到新的一行数据，再调用引擎接口写入这行新数据。
3. 引擎将这行新数据更新到内存中，同时将这个更新操作记录到 redo log 里面，此时 redo log 处于 prepare 状态。然后告知执行器执行完成了，随时可以提交事务。
4. 执行器生成这个操作的 binlog，并把 binlog 写入磁盘。
5. 执行器调用引擎的提交事务接口，引擎把刚刚写入的 redo log 改成提交（commit）状态，更新完成。

执行过程如下图：

![执行器和InnoDB执行一条更新语句的过程](../image/mysql/mysql日志体系/更新语句执行顺序.webp)

最后三步看上去有点“绕”，将 redo log 的写入拆成了两个步骤：prepare 和 commit，这就是"两阶段提交"。



# 两阶段提交 todo




# 如何根据redo log日志文件和binlog日志文件恢复数据 





## 必要性 todo

## 如何实现 todo

# 参考资料
极客时间《MySQL实战45讲》课程


