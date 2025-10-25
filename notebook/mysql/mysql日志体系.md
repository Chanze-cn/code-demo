# MySQL的重要日志文件
mysql的重要日志文件包括redolog日志文件和binlog日志文件。

# 重要的日志文件：redolog日志


redolog日志文件是InnoDB引擎内置的日志文件，属于引擎层的日志文件，当一条记录要更新的时候，InnoDB引擎会先写到redo log日志文件里面，并更新内存，这个时候就算更新完成了。同时，InnoDB会在适当的时候，将redo log日志文件中的内容刷新到磁盘文件中。redo log日志文件用于保证MySQL宕机后，数据可以恢复到宕机前的状态。

## redo log日志文件的结构

![redo log日志文件的结构](../image/mysql/mysql日志体系/redolog模型示意图.webp)

redo log日志文件是一个循环队列，会有两个指针，一个指向队头一个指向队尾，队头到队尾之间就是redo log的日志内容，当队尾指针等于队头-1 时，表示队列满了；当队尾指针等于队头时，表示队列空了。写redo log日志文件的时候，移动队尾指针，将redo log日志文件的内容写入硬盘时，移动队头指针。

## redo log日志文件的作用
redo log日志文件的作用是保证MySQL宕机后，数据可以恢复到宕机前的状态。当MySQL宕机后，InnoDB引擎会读取redo log日志文件中的内容，将数据恢复到宕机前的状态。
> 有了redo log日志文件，InnoDB引擎就可以保证即使数据库异常重启，之前提交的数据也不会丢失，这个能力成为crash-safe能力。整个过程用到的技术称之为WAL(Write-Ahead Logging)技术。即先写日志，再写磁盘

# 重要的日志文件：binlog日志 todo


# 如何根据redo log日志文件和binlog日志文件恢复数据 todo



# 两阶段提交 todo

什么是两阶段提交的定义 todo

## 必要性 todo

## 如何实现 todo


