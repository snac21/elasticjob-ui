# ElasticJob-UI

[![License](https://img.shields.io/badge/license-Apache%202-4EB1BA.svg)](https://www.apache.org/licenses/LICENSE-2.0.html)

[English document](https://github.com/apache/shardingsphere-elasticjob-ui/blob/master/README.md)

## 概述

ElasticJob-UI 是 [ElasticJob](http://shardingsphere.apache.org/elasticjob/) 的管理控制台，包含了动态配置、作业管控等功能。
本项目包括两部分：

- shardingsphere-elasticjob-lite-ui：ElasticJob-Lite 管控端
- shardingsphere-elasticjob-cloud-ui：ElasticJob-Cloud 管控端

### ElasticJob-UI 前端

shardingsphere-elasticjob-lite-ui-frontend & shardingsphere-elasticjob-cloud-ui-frontend 模块基于 [vue](https://github.com/vuejs/vue)，
并使用其相关 UI 工具包 [element](https://github.com/ElemeFE/element) 开发。

### ElasticJob-UI 后端

shardingsphere-elasticjob-lite-ui-backend & shardingsphere-elasticjob-cloud-ui-backend 模块是标准的 Spring Boot 项目。

## 如何构建
## 先编译依赖项目
```
git clone https://github.com/snac21/elasticjob
mvn clean -U install -DskipTests
```

### 再初始化数据库
```
CREATE TABLE `JOB_EXECUTION_LOG` (
  `auto_id` int NOT NULL AUTO_INCREMENT,
  `id` varchar(40) COLLATE utf8mb4_0900_bin NOT NULL,
  `job_name` varchar(100) COLLATE utf8mb4_0900_bin NOT NULL,
  `task_id` varchar(255) COLLATE utf8mb4_0900_bin NOT NULL,
  `hostname` varchar(255) COLLATE utf8mb4_0900_bin NOT NULL,
  `ip` varchar(50) COLLATE utf8mb4_0900_bin NOT NULL,
  `sharding_item` int NOT NULL,
  `execution_source` varchar(20) COLLATE utf8mb4_0900_bin NOT NULL,
  `failure_cause` varchar(4000) COLLATE utf8mb4_0900_bin DEFAULT NULL,
  `is_success` int NOT NULL,
  `start_time` timestamp NULL DEFAULT NULL,
  `complete_time` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`auto_id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=50 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_bin;

CREATE TABLE `JOB_STATUS_TRACE_LOG` (
  `auto_id` int NOT NULL AUTO_INCREMENT,
  `id` varchar(40) COLLATE utf8mb4_0900_bin NOT NULL,
  `job_name` varchar(100) COLLATE utf8mb4_0900_bin NOT NULL,
  `original_task_id` varchar(255) COLLATE utf8mb4_0900_bin NOT NULL,
  `task_id` varchar(255) COLLATE utf8mb4_0900_bin NOT NULL,
  `slave_id` varchar(50) COLLATE utf8mb4_0900_bin NOT NULL,
  `execution_type` varchar(20) COLLATE utf8mb4_0900_bin NOT NULL,
  `sharding_item` varchar(100) COLLATE utf8mb4_0900_bin NOT NULL,
  `state` varchar(20) COLLATE utf8mb4_0900_bin NOT NULL,
  `message` varchar(4000) COLLATE utf8mb4_0900_bin DEFAULT NULL,
  `creation_time` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`auto_id`),
  KEY `TASK_ID_STATE_INDEX` (`task_id`(128),`state`)
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_bin;

```
### 然后配置数据库
```
在backend的src/main/resources/application.properties修改

spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.url=jdbc:mysql://localhost:3306/e-job?useSSL=false&useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=root
```

### 启动backend