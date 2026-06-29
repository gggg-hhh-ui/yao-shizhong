# 爻时钟

简洁美观的 Android 闹钟应用，使用 Jetpack Compose 构建。

## 功能

- 🕐 **实时时钟** — 大字显示当前时间，每秒自动刷新
- ⏰ **闹钟管理** — 添加、编辑、删除闹钟
- 🔁 **重复设置** — 支持每天、工作日、周末、自定义重复
- 📝 **自定义标签** — 为闹钟添加名称标签
- 📳 **震动提醒** — 支持震动模式
- 🔔 **闹钟响铃** — 全屏闹钟界面，支持关闭/稍后提醒
- 🌙 **深色模式** — 自动跟随系统主题

## 技术栈

- Kotlin
- Jetpack Compose
- Material Design 3
- MVVM 架构
- Android AlarmManager
- Android Notification

## 构建

### 前置要求

- JDK 17+
- Android SDK API 34

### 命令行构建

```bash
./gradlew assembleDebug
```

APK 文件输出在 `app/build/outputs/apk/debug/app-debug.apk`

### CI 自动构建

推送代码后，GitHub Actions 自动构建 APK，可在 Actions 页下载。

## 安装

将 `app-debug.apk` 传到手机，直接安装即可。

## 许可证

MIT
