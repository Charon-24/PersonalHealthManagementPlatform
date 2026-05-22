# Personal Health Management Platform

个人健康管理与运动追踪平台（后端）项目。

## 1. 项目简介

本项目基于 Spring Boot 构建，目标是提供健康数据管理、运动计划与记录、饮食记录、健康报告等能力。  
当前仓库已完成：

1. 需求/系统/接口/数据库设计文档沉淀。
2. 数据库初始化 SQL 脚本。
3. 后端通用基础层（统一响应、错误码、异常处理、分页模型等）。

## 2. 技术栈

1. Java 21
2. Spring Boot 3.0.2
3. Spring MVC
4. Spring Validation
5. Maven 3.9.14
6. MySQL 8.0（数据库设计与脚本目标版本）

## 3. 目录结构

```text
PersonalHealthManagementPlatform
|-- design/                         # 设计文档
|   |-- 系统设计文档.md
|   |-- 接口设计文档.md
|   `-- 数据库设计文档.md
|-- requirement/                    # 需求文档
|-- src/main/java/com/peace/personalhealthmanagementplatform
|   |-- PersonalHealthManagementPlatformApplication.java
|   `-- common/                     # 通用基础层
|       |-- constant/               # 常量
|       |-- error/                  # 错误码
|       |-- exception/              # 业务异常
|       |-- handler/                # 全局异常处理
|       |-- request/                # 通用请求 DTO
|       |-- response/               # 通用响应 VO
|       `-- util/                   # 工具类
`-- src/main/resources
    `-- db/V1__init_schema.sql      # 初始化建表脚本
```

## 4. 快速开始

### 4.1 环境要求

1. JDK 21+
2. Maven 3.9.14+
3. MySQL 8.0+

### 4.2 初始化数据库

1. 创建数据库（示例）：`personal_health_management`
2. 执行脚本：`src/main/resources/db/V1__init_schema.sql`

### 4.3 编译与启动

```bash
mvn clean compile
mvn spring-boot:run
```

也可直接在 IDE 运行启动类：

`com.peace.personalhealthmanagementplatform.PersonalHealthManagementPlatformApplication`

默认端口为 `8080`（见 `src/main/resources/application.properties`）。

## 5. API 规范摘要

完整规范请看：`design/接口设计文档.md`。

### 5.1 基础规范

1. Base Path：`/api/v1`
2. 数据格式：`application/json; charset=utf-8`
3. 鉴权头：`Authorization: Bearer <token>`
4. 时间格式：`yyyy-MM-dd HH:mm:ss`（或 `yyyy-MM-dd`）

### 5.2 统一响应体

```json
{
  "code": 0,
  "message": "success",
  "data": {},
  "traceId": "a2bc3d4e5f6g",
  "timestamp": "2026-05-22 20:00:00"
}
```

### 5.3 通用错误码

| code | 含义 |
| --- | --- |
| 0 | 成功 |
| 40001 | 参数错误 |
| 40101 | 未登录 |
| 40301 | 无权限 |
| 40401 | 资源不存在 |
| 40901 | 资源冲突 |
| 42201 | 业务校验失败 |
| 50001 | 系统异常 |

## 6. 通用基础层说明（已实现）

1. `ApiResponse<T>`：统一响应封装（`code/message/data/traceId/timestamp`）
2. `PageVO<T>`：统一分页响应模型
3. `PageQueryDTO`：通用分页请求参数（含默认值和最大页大小限制）
4. `ErrorCode`：平台错误码枚举
5. `BusinessException`：业务异常基类
6. `GlobalExceptionHandler`：全局异常拦截并统一响应
7. `TraceIdUtil`：traceId 获取/生成工具

## 7. 相关文档

1. `design/系统设计文档.md`
2. `design/接口设计文档.md`
3. `design/数据库设计文档.md`
4. `src/main/resources/db/V1__init_schema.sql`

## 8. 当前开发状态

1. 已完成：平台通用层与数据库脚本。
2. 待完成：按设计文档逐模块实现 Controller/Service/Repository 与业务测试。
