package com.yao.shizhong

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

/**
 * 开机自启动接收器
 * 设备启动完成后自动打开时钟应用
 */
class BootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_BOOT_COMPLETED) {
            val launchIntent = Intent(context, MainActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(launchIntent)
        }
    }
}
