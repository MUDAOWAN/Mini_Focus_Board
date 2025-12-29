# Mini Focus Board - Android Mini App

一款基于Android系统的轻量级待办事项管理应用，集成了励志语录功能。

## 功能特性

-  **待办事项管理**：添加、标记完成、删除任务
-  **励志语录**：从网络API获取随机励志语录，支持一键刷新
-  **本地存储**：使用SharedPreferences持久化保存任务数据
-  **网络请求**：使用OkHttp进行网络请求获取语录
-  **Material Design**：采用Material Design设计规范，界面美观现代
-  **性能优化**：使用RecyclerView实现流畅列表滚动，无资源泄露

## 技术栈

- **开发语言**：Kotlin
- **UI框架**：Android Material Components
- **网络库**：OkHttp 4.12.0
- **JSON解析**：Gson 2.10.1
- **架构模式**：MVVM（使用ViewBinding和Lifecycle）
- **最低SDK版本**：24 (Android 7.0)
- **目标SDK版本**：34 (Android 14)

## 构建和运行

### 前置要求

- Android Studio Hedgehog (2023.1.1) 或更高版本
- JDK 8 或更高版本
- Android SDK (API 24+)

### 使用

1. 克隆或下载项目到本地
2. 使用Android Studio打开项目
3. 等待Gradle同步完成
4. 连接Android设备或启动模拟器
5. 点击运行按钮或使用快捷键 `Shift+F10`

## 技术实现要点

### 1. 网络请求（技术要求）
- 使用OkHttp库进行HTTP请求
- 调用 `https://api.quotable.io/random` API获取随机语录
- 使用Kotlin Coroutines实现异步网络请求，避免阻塞UI线程
- 网络错误时显示友好提示，保留上次获取的语录

### 2. 本地存储（技术要求）
- 使用SharedPreferences存储任务列表和语录
- 任务列表序列化为JSON格式保存
- 应用启动时自动加载保存的数据
- 数据变更时实时保存

### 3. 性能优化
- 使用RecyclerView实现列表，支持大量任务流畅滚动
- ViewBinding减少findViewById调用，提升性能
- 正确管理OkHttp Client生命周期，防止内存泄露
- 使用Lifecycle感知组件，避免在Activity销毁后执行操作

## 许可证

本项目仅用于学习目的。
