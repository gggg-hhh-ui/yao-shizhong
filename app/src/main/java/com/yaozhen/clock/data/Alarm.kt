package com.yaozhen.clock.data

import java.io.Serializable

data class Alarm(
    val id: Long = System.currentTimeMillis(),
    val hour: Int,
    val minute: Int,
    val label: String = "",
    val isEnabled: Boolean = true,
    val repeatDays: Set<Int> = setOf(), // 0=周日,1=周一...6=周六
    val vibrate: Boolean = true
) : Serializable {

    val timeString: String
        get() = String.format("%02d:%02d", hour, minute)

    val repeatString: String
        get() = when {
            repeatDays.isEmpty() -> "只响一次"
            repeatDays.size == 7 -> "每天"
            repeatDays == setOf(1, 2, 3, 4, 5) -> "工作日"
            repeatDays == setOf(0, 6) -> "周末"
            else -> {
                val dayNames = listOf("周日", "周一", "周二", "周三", "周四", "周五", "周六")
                repeatDays.sorted().joinToString(", ") { dayNames[it] }
            }
        }

    fun isNextDay(): Boolean {
        val now = java.util.Calendar.getInstance()
        val alarmTime = java.util.Calendar.getInstance().apply {
            set(java.util.Calendar.HOUR_OF_DAY, hour)
            set(java.util.Calendar.MINUTE, minute)
            set(java.util.Calendar.SECOND, 0)
            set(java.util.Calendar.MILLISECOND, 0)
        }
        if (alarmTime.timeInMillis <= now.timeInMillis) {
            alarmTime.add(java.util.Calendar.DAY_OF_MONTH, 1)
        }
        return true
    }
}
