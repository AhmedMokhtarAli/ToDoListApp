package com.example.todolist.Ui.subFeature

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

open class AlarmReciver : BroadcastReceiver() {
    private val NOTIFICATION_ID=14

    @SuppressLint("UnsafeProtectedBroadcastReceiver")
    override fun onReceive(context: Context?, intent: Intent?) {
        val taskName=intent?.getStringExtra("taskName")
        val notificationUtils=NotificationUtils(context!!)
        val notification=notificationUtils.getNotificationBuilder(taskName!!).build()
        notificationUtils.getManager().notify(NOTIFICATION_ID,notification)
    }
}
