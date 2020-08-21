package com.example.reminder.notify

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.reminder.R
import com.example.reminder.db.Event
import com.example.reminder.repository.Repository
import com.example.reminder.ui.DetailScreen
import com.example.reminder.ui.MainActivity
import com.example.reminder.viewmodel.MainViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class NotificationHelper @Inject constructor(context: Context, private val repository: Repository? = null): ContextWrapper(context) {

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


    fun createNotification(id: Long, eventTitle: String, description: String): NotificationCompat.Builder{

        // create pending intent so when the user clicks the notification to go to the particular event
        //--------------------------------------
        //------------------------------------------
        //-----------------------------------

        val intent = Intent(applicationContext, MainActivity::class.java).also { intent->
            intent.action = "Go to detail screen"
            intent.putExtra("1", eventTitle)
            intent.putExtra("2", id)
            intent.putExtra("3", description)
        }


        val vibrateArray = longArrayOf(1500, 1000, 1500)

        return NotificationCompat.Builder(this, chanelId)
            .setAutoCancel(true)
            .setOngoing(true)
            .setContentTitle(eventTitle)
            .setContentText("Tap to see the event")
            .setSmallIcon(R.drawable.notification_img)
            .setContentIntent(mainActivityPendingIntent(intent, id.toInt() + 1))
            .setVibrate(vibrateArray)
    }

    private fun mainActivityPendingIntent(intent: Intent, requestCode: Int): PendingIntent = PendingIntent.getActivity(applicationContext,
        requestCode,
        intent,
        0
    )


}