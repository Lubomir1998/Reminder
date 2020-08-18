package com.example.reminder.notify

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.ContextWrapper
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.reminder.R

class NotificationHelper(context: Context): ContextWrapper(context) {

    private val chanelId = "chanel_id"
    private var notificationManager: NotificationManager? = null

    init {
        createChanel()
    }

    private fun createChanel() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val chanel = NotificationChannel(chanelId, "notificationChanel", NotificationManager.IMPORTANCE_DEFAULT)

            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(chanel)
        }
    }

    fun getManager(): NotificationManager {
        if(notificationManager == null) {
            notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        }

        return notificationManager as NotificationManager
    }


    fun createNotification(eventTitle: String): NotificationCompat.Builder{

        // create pending intent so when the user clicks the notification to go to the particular event
        //--------------------------------------
        //------------------------------------------
        //-----------------------------------

        val vibrateArray = longArrayOf(1500)

        return NotificationCompat.Builder(this, chanelId)
            .setContentTitle(eventTitle)
            .setContentText("Tap to see the event")
            .setSmallIcon(R.drawable.notification_img)
            .setVibrate(vibrateArray)
    }



}