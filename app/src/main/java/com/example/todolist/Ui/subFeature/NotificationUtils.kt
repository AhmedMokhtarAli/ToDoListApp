package com.example.todolist.Ui.subFeature

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.example.todolist.R
import com.example.todolist.Ui.ToDoActivity

class NotificationUtils(context: Context) : ContextWrapper(context) {
    val CHANNEL_ID="TO DO ALERT NOTIFICATION ID"
    val CHANNEL_NAME="TODO ALERT NOTIFICATION"
    private val REQUES_CODE: Int = 100


    private var notificationManager:NotificationManager?=null

    init {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            creatChannel()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun creatChannel(){
        val  channel =NotificationChannel(CHANNEL_ID,CHANNEL_NAME,NotificationManager.IMPORTANCE_HIGH)
        channel.enableVibration(true)
        getManager().createNotificationChannel(channel)
    }

    fun getManager():NotificationManager{
        if (notificationManager==null) notificationManager=getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        return notificationManager as NotificationManager
    }

    fun getNotificationBuilder():NotificationCompat.Builder{
        val intent= Intent(this,ToDoActivity::class.java).apply {
            flags=Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent=PendingIntent.getActivity(this,REQUES_CODE,intent,0)
        return NotificationCompat.Builder(applicationContext,CHANNEL_ID)
            .setSmallIcon(R.drawable.task)
            .setContentTitle("TODO")
            .setContentText("you have a new task to do")
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .setContentIntent(pendingIntent)
    }
}