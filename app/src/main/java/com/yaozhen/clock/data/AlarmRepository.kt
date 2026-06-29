package com.yaozhen.clock.data

import android.content.Context
import android.content.SharedPreferences
import org.json.JSONArray
import org.json.JSONObject

class AlarmRepository(context: Context) {

    private val prefs: SharedPreferences = context.getSharedPreferences(
        PREFS_NAME, Context.MODE_PRIVATE
    )

    fun getAlarms(): List<Alarm> {
        val json = prefs.getString(KEY_ALARMS, "[]") ?: "[]"
        return try {
            val array = JSONArray(json)
            (0 until array.length()).map { i ->
                val obj = array.getJSONObject(i)
                Alarm(
                    id = obj.getLong("id"),
                    hour = obj.getInt("hour"),
                    minute = obj.getInt("minute"),
                    label = obj.optString("label", ""),
                    isEnabled = obj.optBoolean("isEnabled", true),
                    repeatDays = (0 until obj.getJSONArray("repeatDays").length())
                        .map { obj.getJSONArray("repeatDays").getInt(it) }.toSet(),
                    vibrate = obj.optBoolean("vibrate", true)
                )
            }
        } catch (e: Exception) {
            emptyList()
        }
    }

    fun saveAlarm(alarm: Alarm) {
        val alarms = getAlarms().toMutableList()
        val existingIndex = alarms.indexOfFirst { it.id == alarm.id }
        if (existingIndex >= 0) {
            alarms[existingIndex] = alarm
        } else {
            alarms.add(alarm)
        }
        saveAlarms(alarms)
    }

    fun deleteAlarm(alarmId: Long) {
        val alarms = getAlarms().filter { it.id != alarmId }
        saveAlarms(alarms)
    }

    fun getAlarm(alarmId: Long): Alarm? {
        return getAlarms().find { it.id == alarmId }
    }

    private fun saveAlarms(alarms: List<Alarm>) {
        val array = JSONArray()
        alarms.forEach { alarm ->
            val obj = JSONObject().apply {
                put("id", alarm.id)
                put("hour", alarm.hour)
                put("minute", alarm.minute)
                put("label", alarm.label)
                put("isEnabled", alarm.isEnabled)
                put("repeatDays", JSONArray(alarm.repeatDays.toList()))
                put("vibrate", alarm.vibrate)
            }
            array.put(obj)
        }
        prefs.edit().putString(KEY_ALARMS, array.toString()).apply()
    }

    companion object {
        private const val PREFS_NAME = "yao_shizhong_alarms"
        private const val KEY_ALARMS = "alarms"
    }
}
