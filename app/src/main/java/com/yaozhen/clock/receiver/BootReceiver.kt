package com.yaozhen.clock.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.yaozhen.clock.data.AlarmRepository
import com.yaozhen.clock.data.AlarmScheduler

class BootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_BOOT_COMPLETED) {
            val repository = AlarmRepository(context)
            val scheduler = AlarmScheduler(context)
            val alarms = repository.getAlarms()
            scheduler.rescheduleAll(alarms)
        }
    }
}
