package com.example.todolist.Ui

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.todolist.R

open class AlarmReciver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val alarmIntent=Intent(context,DestniationActivity::class.java)
        intent!!.flags=Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        val pendingIntent=PendingIntent.getActivity(context,0,alarmIntent,0)

        val builder=NotificationCompat.Builder(context!!,"TODO")
            .setSmallIcon(R.drawable.task)
            .setContentTitle("TODO")
            .setContentText("you have a new task to do")
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .setContentIntent(pendingIntent)

        val notificationManager=NotificationManagerCompat.from(context!!)
        notificationManager.notify(123,builder.build())
    }
}
