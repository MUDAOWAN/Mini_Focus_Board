# Mini Focus Board - Android Mini App

一款基于Android系统的轻量级待办事项管理应用，集成了励志语录功能。

## 功能特性

- ✅ **待办事项管理**：添加、标记完成、删除任务
- 📝 **励志语录**：从网络API获取随机励志语录，支持一键刷新
- 💾 **本地存储**：使用SharedPreferences持久化保存任务数据
- 🌐 **网络请求**：使用OkHttp进行网络请求获取语录
- 🎨 **Material Design**：采用Material Design设计规范，界面美观现代
- ⚡ **性能优化**：使用RecyclerView实现流畅列表滚动，无资源泄露

## 技术栈

- **开发语言**：Kotlin
- **UI框架**：Android Material Components
- **网络库**：OkHttp 4.12.0
- **JSON解析**：Gson 2.10.1
- **架构模式**：MVVM（使用ViewBinding和Lifecycle）
- **最低SDK版本**：24 (Android 7.0)
- **目标SDK版本**：34 (Android 14)

## 项目结构

```
app/
├── src/main/
│   ├── java/com/minifocusboard/
│   │   ├── MainActivity.kt          # 主Activity，包含业务逻辑
│   │   └── TaskAdapter.kt           # RecyclerView适配器
│   ├── res/
│   │   ├── layout/
│   │   │   ├── activity_main.xml    # 主界面布局
│   │   │   └── item_task.xml        # 任务项布局
│   │   ├── values/
│   │   │   ├── strings.xml          # 字符串资源
│   │   │   ├── colors.xml           # 颜色资源
│   │   │   └── themes.xml           # 主题样式
│   │   └── xml/
│   │       ├── backup_rules.xml     # 备份规则
│   │       └── data_extraction_rules.xml
│   └── AndroidManifest.xml          # 应用清单文件
├── build.gradle.kts                 # 应用级构建配置
└── proguard-rules.pro               # ProGuard规则

build.gradle.kts                     # 项目级构建配置
settings.gradle.kts                  # Gradle设置
gradle.properties                    # Gradle属性
```

## 构建和运行

### 前置要求

- Android Studio Hedgehog (2023.1.1) 或更高版本
- JDK 8 或更高版本
- Android SDK (API 24+)

### 构建步骤

1. 克隆或下载项目到本地
2. 使用Android Studio打开项目
3. 等待Gradle同步完成
4. 连接Android设备或启动模拟器
5. 点击运行按钮或使用快捷键 `Shift+F10`

### 使用Gradle命令行构建

```bash
# 构建Debug版本
./gradlew assembleDebug

# 构建Release版本
./gradlew assembleRelease

# 安装到设备
./gradlew installDebug
```

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

## 提交要求

1. **产品报告**：见 `Product_Report.md`
2. **源代码**：已准备好提交到GitHub/Gitee
3. **演示录屏**：需要录制应用使用视频（mkv, mp4, avi, rm, rmvb格式）

## 许可证

本项目仅用于学习目的。
