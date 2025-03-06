# 基于SpringBoot3 + Vue3 + Vite的前后端分离博客系统（支持AI工具）

<p align="center">      </a> </p> <p align="center">    基于SpringBoot3 + Vue3 + Vite开发的前后端分离博客系统，集成AI对话问答、图生文、图生视频等智能功能 </p> <p align="center">    <a target="_blank" href="https://github.com/KeepInlove/ai-blog">       <img src="https://img.shields.io/badge/JDK-21-green"/>       <img src="https://img.shields.io/badge/springboot-3.3.3-green"/>       <img src="https://img.shields.io/badge/vue-3.x-green"/>       <img src="https://img.shields.io/badge/vite-5.x-green"/>       <img src="https://img.shields.io/badge/mysql-8.0.20-green"/>       <img src="https://img.shields.io/badge/mybatis--plus-3.5.7-green"/>       <img src="https://img.shields.io/badge/redis-6.0.5-green"/>       <img src="https://img.shields.io/badge/elasticsearch-7.9.2-green"/>       <img src="https://img.shields.io/badge/rabbitmq-3.8.5-green"/>       <img src="https://img.shields.io/badge/AI功能-对话%7C图生文%7C图生视频-blue"/>    </a> </p>

------

## 在线地址

- **前台地址：** 暂未上线
- **后台地址**：暂未上线
- **在线API文档地址：** 暂未上线

测试账号：`test@qq.com`，密码：`1234567`

------

## 技术架构

### 前端

- Vue3
- Vite
- Vue Router
- Pinia
- Element Plus
- Axios
- ECharts

### 后端

- SpringBoot 3.3.3
- Spring Security 6
- Spring Data Redis
- Spring Data Elasticsearch
- Spring AMQP
- MyBatis Plus
- RabbitMQ
- Redis
- Elasticsearch
- MySQL 8
- Knife4j（Swagger3增强）

### AI功能支持

- AI对话问答（支持多种大模型接口接入）
- 图生文（AI图像内容描述）
- 图生视频（基于AI视频生成能力）

------

## 项目特点

- 前后端分离，基于Vue3+Vite构建，响应速度更快。
- 支持AI功能：
  - 智能对话问答：内置多种AI模型接口，支持个性化聊天。
  - 图生文：上传图片，AI生成描述文本。
  - 图生视频：通过图像生成短视频，支持风格化生成。
- Token认证（替代原Session登录机制），前后端分离更加彻底。
- 全新UI，前台风格参考“Hexo-Butterfly”，后台参考“Element-Admin”，简洁大方。
- Markdown编辑器，支持代码高亮、图片预览、深色模式等。
- 在线聊天室，支持撤回、语音输入、未读消息统计等功能。
- 评论区支持表情回复，并带有弹幕留言墙功能。
- 搜索文章支持Elasticsearch高亮分词。
- 支持发布图文说说、音乐播放器、文章目录推荐等功能。
- 后台支持个性化配置，动态修改背景图、主题、博客信息。
- 代码支持多种搜索模式（MySQL全文/Elasticsearch），多种上传模式（OSS/本地），开箱即用。
- 新增AOP日志注解，自动记录重要操作日志。
- 支持动态权限修改（RBAC），前端菜单与后端权限实时同步更新。

------

## 目录结构

```
pgsql复制编辑blog-springboot
├── annotation        --  自定义注解
├── aspect            --  AOP切面模块
├── config            --  配置模块
├── constant          --  常量模块
├── consumer          --  MQ消费者模块
├── controller        --  控制器模块
├── dao               --  数据访问层
├── dto               --  数据传输对象
├── enums             --  枚举类
├── exception         --  自定义异常
├── handler           --  全局异常与安全处理器
├── service           --  服务层
├── strategy          --  策略模式扩展（文件上传、登录模式、搜索模式等）
├── util               --  工具类
└── vo                --  视图对象
```

------

## 运行环境

| 环境          | 版本   |
| ------------- | ------ |
| JDK           | 21     |
| MySQL         | 8.0.20 |
| Redis         | 6.0.5  |
| Elasticsearch | 7.9.2  |
| RabbitMQ      | 3.8.5  |

------

## 开发环境

| 工具名   | 说明         |
| -------- | ------------ |
| IDEA     | Java后端开发 |
| webstorm | 前端开发     |
| Navicat  | 数据库管理   |
| tiny-rdm | Redis可视化  |

------

## 快速开始

### 1. 数据库准备

执行`blog-mysql8.sql`初始化数据库，修改`application.yml`数据库连接信息。

### 2. Redis、Elasticsearch、RabbitMQ配置

参考`application.yml`和`docker-compose.yml`，确保Redis、ES、RabbitMQ正常运行。

### 3. AI功能配置

- 对话问答：支持接入OpenAI、deepseek等大模型。
- 图生文：支持调用阿里云/百度智能视觉接口。
- 图生视频：支持调用腾讯云或其他第三方视频生成接口。

### 4. 启动项目

- 启动后端：`BlogSpringBootApplication`
- 启动前端：`npm install` + `npm run dev`

### 5. 访问地址

- 前台：http://localhost:5173
- 后台：http://localhost:5173/admin
- 接口文档：http://localhost:8080/doc.html

------

## 注意事项

- AI功能依赖第三方接口，需提前申请对应API Key。
- 博客信息、背景图等可在后台“网站配置”模块实时调整。
- 前端所有配置动态加载，后端控制，方便二次开发。

## 版权与许可

本项目基于开源项目 [blog](http://www.talkxj.com) 进行改进和功能扩展。  
原项目由 www.talkxj.com 发布，并遵循 [Apache 2.0 License](http://www.apache.org/licenses/LICENSE-2.0) 许可协议。  
本项目所有新增与修改部分同样遵循 [Apache 2.0 License](http://www.apache.org/licenses/LICENSE-2.0)。

无论以任何形式获取或使用本项目代码，均需遵守上述许可协议。

如需商业授权或合作，请联系：gxy06x@qq.com

