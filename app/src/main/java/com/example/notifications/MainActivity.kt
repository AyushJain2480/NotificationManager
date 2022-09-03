package com.example.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.core.app.NotificationChannelCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class MainActivity : AppCompatActivity() {

    val CHANNEL_ID = "channelID"
    val CHANNEL_NAME = "channelName"
    val NOTIFICATION_ID = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // creating notification channel
        createNotificaTionChannel()


        val intent = Intent(this,MainActivity::class.java)
        val pendingIntent = TaskStackBuilder.create(this).run{
            addNextIntentWithParentStack(intent)
            getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT)
        }

        // next step is to post our notification on this channel
        val notification = NotificationCompat.Builder(this,CHANNEL_ID)
            .setContentTitle("Look at this")
            .setContentText("hi if you like rate us")
            .setSmallIcon(R.drawable.export)
            .setPriority(NotificationCompat.PRIORITY_HIGH) // this notif will popped up first if there is another notif coming on the same time
            .setContentIntent(pendingIntent)
            .build()


        val notificationManager = NotificationManagerCompat.from(this)

        val button = findViewById<Button>(R.id.show_notification)
        button.setOnClickListener{
            notificationManager.notify(NOTIFICATION_ID,notification)
        }
    }

    fun createNotificaTionChannel(){
        // notification channels work for versions upper than oreo
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
          val channel = NotificationChannel(CHANNEL_ID,CHANNEL_NAME,
              NotificationManager.IMPORTANCE_DEFAULT).apply{ // here importance for notif channel means sound , default and other effect in our notif
                lightColor = Color.GREEN
                enableLights(true)
          }
            //creating notificationManger on NotificationChannel
          val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
          manager.createNotificationChannel(channel)
        }
    }
}