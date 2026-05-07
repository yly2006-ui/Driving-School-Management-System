# 项目概述

本项目是基于 **若依 (RuoYi) 框架** 的二次开发，面向“马上学车”业务的完整实现。项目结构遵循若依的标准模块化设计，主要包含以下子模块：

- **ruoyi-admin**: 管理后台，提供系统设置、用户管理、角色权限等通用功能。
- **ruoyi-common**: 公共依赖模块，封装了工具类、常量、枚举等在各模块之间共享的代码。
- **ruoyi-framework**: 若依核心框架，包含 SpringBoot、MyBatis、Shiro 等基础设施的配置。
- **ruoyi-system**: 系统基础模块，负责字典、参数、日志等基础业务。
- **ruoyi-quartz**: 定时任务模块，基于 Quartz 实现任务调度。
- **ruoyi-generator**: 代码生成器，能够根据数据库表快速生成增删改查页面。
- **ruoyi-driving**: 学车业务模块，核心业务逻辑实现，包括报名、预约、练车、考试等。
- **mashang-driving**: 与 “马上学车” 项目相关的特定实现（如第三方接口、业务扩展）。

## 项目特性

- **基于若依脚手架**：快速搭建后台管理系统，支持多租户、权限控制。
- **模块化设计**：每个业务域独立模块，便于维护和二次扩展。
- **集成 Knife4j**：在 `ruoyi-admin` 中已经集成了 Knife4j，用于自动生成 Swagger 文档。
- **Dockerfile & Jenkinsfile**：提供容器化部署与 CI 流水线脚本。

## 快速启动

```bash
# 克隆代码（已在本地）
cd D:/ms-stu-pro-339-server

# 修改配置（数据库、端口等）
# ruoyi-admin/src/main/resources/application.yml 中的 TODO 项目

# 编译并运行（Maven）
mvn clean package -DskipTests
java -jar target/ruoyi.jar
```

## 代码贡献流程

1. **创建分支**：`git checkout -b feature/your-feature`
2. **本地开发**，完成后提交 `git commit -m "描述"`
3. **推送分支**：`git push -u github feature/your-feature`
4. **在 GitHub 创建 Pull Request**，通过 CI 检查后合并。

---

如需更多信息，请查看各模块的 `README.md` 或查阅 `doc/` 目录下的设计文档。
