# 资源文件说明

## 应用图标

应用需要以下图标文件（可以使用Android Studio的Image Asset工具生成）：

- `mipmap-mdpi/ic_launcher.png` (48x48)
- `mipmap-hdpi/ic_launcher.png` (72x72)
- `mipmap-xhdpi/ic_launcher.png` (96x96)
- `mipmap-xxhdpi/ic_launcher.png` (144x144)
- `mipmap-xxxhdpi/ic_launcher.png` (192x192)
- `mipmap-anydpi-v26/ic_launcher.xml` (已创建)
- `mipmap-anydpi-v26/ic_launcher_round.xml` (已创建)

以及对应的 `ic_launcher_round.png` 和 `ic_launcher_foreground.png` 文件。

## 生成图标

在Android Studio中：
1. 右键 `res` 文件夹 → New → Image Asset
2. 选择 Launcher Icons (Adaptive and Legacy)
3. 设计你的图标
4. 点击 Next → Finish

或者使用在线工具生成后放入对应文件夹。

