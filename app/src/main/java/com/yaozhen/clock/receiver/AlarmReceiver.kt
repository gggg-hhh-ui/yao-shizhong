package com.yaozhen.clock.receiver

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.media.RingtoneManager
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import androidx.core.app.NotificationCompat
import com.yaozhen.clock.ClockApplication
import com.yaozhen.clock.MainActivity
import com.yaozhen.clock.R
import com.yaozhen.clock.data.AlarmRepository
import com.yaozhen.clock.data.AlarmScheduler
import com.yaozhen.clock.ui.AlarmRingActivity

class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action != "com.yaozhen.clock.ALARM_TRIGGER") return

        val alarmId = intent.getLongExtra("alarm_id", -1)
        val label = intent.getExtra("alarm_label", "")
        val vibrate = intent.getBooleanExtra("alarm_vibrate", true)

        if (alarmId == -1L) return

        val repository = AlarmRepository(context)
        val alarm = repository.getAlarm(alarmId)

        if (alarm == null || !alarm.isEnabled) return

        val scheduler = AlarmScheduler(context)
        scheduler.scheduleAlarm(alarm)

        showAlarmNotification(context, alarmId, label)
        startAlarmRingActivity(context, alarmId, label)

        if (vibrate) {
            startVibration(context)
        }
    }

    private fun showAlarmNotification(context: Context, alarmId: Long, label: String) {
        val openIntent = Intent(context, AlarmRingActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            putExtra("alarm_id", alarmId)
            putExtra("alarm_label", label)
        }

        val pendingIntent = PendingIntent.getActivity(
            context,
            alarmId.toInt(),
            openIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val defaultRingtone = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
            ?: RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        val notification = NotificationCompat.Builder(context, ClockApplication.CHANNEL_ALARM)
            .setSmallIcon(android.R.drawable.ic_lock_id_alarm)
            .setContentTitle(if (label.isNotEmpty()) label else "闹钟响了")
            .setContentText(if (label.isNotEmpty()) "闹钟响了" else "该起床了")
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setCategory(NotificationCompat.CATEGORY_ALARM)
            .setFullScreenIntent(pendingIntent, true)
            .setAutoCancel(true)
            .setSound(defaultRingtone)
            .build()

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(alarmId.toInt(), notification)
    }

    private fun startAlarmRingActivity(context: Context, alarmId: Long, label: String) {
        val ringIntent = Intent(context, AlarmRingActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            putExtra("alarm_id", alarmId)
            putExtra("alarm_label", label)
        }
        context.startActivity(ringIntent)
    }

    private fun startVibration(context: Context) {
        val vibrator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val vibratorManager =
                context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
            vibratorManager.defaultVibrator
        } else {
            @Suppress("DEPRECATION")
            context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        }

        val pattern = longArrayOf(0, 500, 200, 500, 200, 500)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createWaveform(pattern, 0))
        } else {
            @Suppress("DEPRECATION")
            vibrator.vibrate(pattern, 0)
        }
    }
}
