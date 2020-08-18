package com.example.reminder.notify

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.reminder.db.Event
import com.example.reminder.repository.Repository
import javax.inject.Inject


class AlertReceiver: BroadcastReceiver(){

    private lateinit var notificationHelper: NotificationHelper


    override fun onReceive(context: Context?, intent: Intent?) {


        notificationHelper = context?.let {
            NotificationHelper(it)
        } ?: return

        val eventTitle = intent?.getStringExtra("key")
        val notificationId = intent?.getLongExtra("key2", -1)
        val pastEvent = Event(notificationId!!, eventTitle!!, intent?.getStringExtra("key3"), true)
        notificationHelper.updateEvent(pastEvent)

        // in createNotification(string) function we give the event title as a parameter
        val notification = notificationHelper.createNotification(eventTitle, context, pastEvent.timeStamp, pastEvent.title, pastEvent.description, pastEvent.isHappened).build()


        Log.d("TAG", "************: $pastEvent")

        notificationHelper.getManager().notify(notificationId.toInt(), notification)


    }

}