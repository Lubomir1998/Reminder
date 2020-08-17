package com.example.reminder.notify

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import javax.inject.Inject

class AlertReceiver
//@Inject constructor(private val notificationHelper: NotificationHelper)
    : BroadcastReceiver(){

    @Inject lateinit var notificationHelper: NotificationHelper

    override fun onReceive(context: Context?, intent: Intent?) {

        notificationHelper = context?.let {
            NotificationHelper(
                it
            )
        } ?: return

        val eventTitle = intent?.getStringExtra("key")

        // in createNotification(string) function we give the event title as a parameter
        val notification = notificationHelper.createNotification(eventTitle!!).build()

        notificationHelper.getManager().notify(1, notification)

    }

}