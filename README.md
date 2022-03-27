# 基于OSS的跨平台文件传输系统--通途

## 实现的功能

1. 文件存储
2. 设备之间文件发送
3. 回收站
4. 文件管理
5. 设备管理
6. 搜索文件
7. 登录，邮箱注册

## TODO

1. 找回密码
2. P2P传输

## 项目结构

[api](src/main/java/com/tongtu/tongtu/api) Rest接口  
[data](src/main/java/com/tongtu/tongtu/data)  JPA仓库  
[domain](src/main/java/com/tongtu/tongtu/domain)  数据库实体  
[exception](src/main/java/com/tongtu/tongtu/exception)  异常和异常处理  
[message](src/main/java/com/tongtu/tongtu/message)  极光消息推送配置和格式  
[mq](src/main/java/com/tongtu/tongtu/mq)  消息队列RabbitMQ配置和监听  
[oss](src/main/java/com/tongtu/tongtu/oss)  阿里云OSS配置和工具  
[search](src/main/java/com/tongtu/tongtu/search)  全文搜索ElasticSearch配置  
[security](src/main/java/com/tongtu/tongtu/security)  登录注册配置  
[task](src/main/java/com/tongtu/tongtu/task)  启动时和定时任务  

 