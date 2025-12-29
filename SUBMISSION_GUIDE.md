# 提交指南

## 1. 准备Git仓库

### 创建GitHub仓库
1. 登录GitHub，点击右上角 "+" → "New repository"
2. 仓库名称：`MiniFocusBoard`（或自定义）
3. 选择 Public 或 Private
4. 不要初始化README、.gitignore或license（项目已包含）
5. 点击 "Create repository"

### 创建Gitee仓库
1. 登录Gitee，点击右上角 "+" → "新建仓库"
2. 仓库名称：`MiniFocusBoard`（或自定义）
3. 选择公开或私有
4. 不要初始化README
5. 点击 "创建"

## 2. 提交代码到仓库

在项目根目录执行以下命令：

```bash
# 初始化Git仓库
git init

# 添加所有文件
git add .

# 提交代码
git commit -m "Initial commit: Mini Focus Board Android App"

# 添加远程仓库（GitHub示例）
git remote add origin https://github.com/你的用户名/MiniFocusBoard.git

# 或者Gitee示例
# git remote add origin https://gitee.com/你的用户名/MiniFocusBoard.git

# 推送到远程仓库
git push -u origin main
# 如果默认分支是master，使用：git push -u origin master
```

## 3. 获取仓库地址

提交后，复制仓库地址：
- GitHub: `https://github.com/你的用户名/MiniFocusBoard`
- Gitee: `https://gitee.com/你的用户名/MiniFocusBoard`

## 4. 创建提交文件

创建一个文本文件（如 `repository_url.txt`），内容为仓库地址：

```
https://github.com/你的用户名/MiniFocusBoard
```

或

```
https://gitee.com/你的用户名/MiniFocusBoard
```

## 5. 提交清单

确保以下文件已提交：

- ✅ 源代码（所有.kt、.xml、.kts文件）
- ✅ 配置文件（build.gradle.kts、AndroidManifest.xml等）
- ✅ README.md
- ✅ Product_Report.md
- ✅ .gitignore
- ✅ repository_url.txt（包含仓库地址）

## 6. 注意事项

- 不要提交 `build/` 目录（已在.gitignore中）
- 不要提交 `.gradle/` 目录
- 不要提交 `local.properties` 文件
- 应用图标文件（.png）如果较大，可以考虑使用Git LFS或提供说明

