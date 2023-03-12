package com.example.todolist.Ui.subFeature

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.icu.util.Calendar
import android.util.Log

import androidx.fragment.app.FragmentActivity

private const val TAG = "Alarm"
class Alarm(val activity: FragmentActivity) {
    private lateinit var alarmManager:AlarmManager
    private lateinit var pendingIntent:PendingIntent

    private val REQUES_CODE: Int = 100

    fun start(calendar: Calendar) {
        alarmManager = activity?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(activity, AlarmReciver::class.java)
        pendingIntent = PendingIntent.getBroadcast(
            activity,
            REQUES_CODE,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent
        )
    }
}