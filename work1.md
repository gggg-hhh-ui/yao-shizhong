# 任务1_数字时间

**仓库：** gggg-hhh-ui/yao-shizhong

**任务思路：**
- 开发安卓时钟应用（中国时区）
- 使用 GitHub Actions 自动构建 APK
- 分支策略：main（仅 README），China-clock（开发分支）

**遇到的错误及解决方法：**

| 错误 | 原因 | 解决方法 |
|------|------|----------|
| `Unknown command-line option '-X'` | gradlew 中 DEFAULT_JVM_OPTS 引号格式问题，被 shell 解析成 `-X` 和 `mx64m` | 多次修改引号格式，最终重写 gradlew 脚本，移除有问题的 `eval set --` 逻辑 |
| `Could not find or load main class "-Xmx64m"` | printf + eval 组合在解析 JVM 参数时有 bug | 清空 DEFAULT_JVM_OPTS |
| `Could not find or load main class`（空类名） | eval 语句在空变量时导致参数丢失 | 彻底简化 gradlew，直接在 `exec "$JAVACMD"` 行传递参数，不再使用 eval |

**任务目前状态：**
- ✅ 构建成功（GitHub Actions run 28352421423）
- ✅ APK 已生成（YaoShizhong-debug）
- ✅ 下载地址：https://github.com/gggg-hhh-ui/yao-shizhong/actions/runs/28352421423
- ⏳ 待安装测试

**APK 功能：**
- 显示北京时间
- 开机自启动
- 全屏显示
- 保持屏幕常亮

**技术栈：**
- Kotlin + Jetpack Compose + Material3
- Compile SDK 34, Min SDK 26
- GitHub Actions 自动构建
