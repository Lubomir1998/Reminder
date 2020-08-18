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

    fun updateEvent(event: Event){
        CoroutineScope(Dispatchers.IO).launch {
            repository?.update(event)
        }
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


    fun createNotification(eventTitle: String, @ApplicationContext context: Context, timeStamp: Long, title: String, description: String, isHapened: Boolean): NotificationCompat.Builder{

        // create pending intent so when the user clicks the notification to go to the particular event
        //--------------------------------------
        //------------------------------------------
        //-----------------------------------

        val intent = Intent(context, DetailScreen::class.java)
        intent.putExtra("tkey", timeStamp)
        intent.putExtra("titlekey", title)
        intent.putExtra("dkey", description)
        intent.putExtra("ikey", isHapened)
        val pendingIntent = PendingIntent.getActivity(context, 99, intent, 0)

        val vibrateArray = longArrayOf(1500)

        return NotificationCompat.Builder(this, chanelId)
            .setContentTitle(eventTitle)
            .setContentText("Tap to see the event")
            .setSmallIcon(R.drawable.notification_img)
            .setContentIntent(pendingIntent)
            .setVibrate(vibrateArray)
    }



}