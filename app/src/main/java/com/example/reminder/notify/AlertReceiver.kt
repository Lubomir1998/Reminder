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

        val eventTitle = intent?.getStringExtra("title")
        val notificationId = intent?.getLongExtra("id", -1)
        val description = intent?.getStringExtra("description")

        // in createNotification(string) function we give the event title as a parameter
        val notification = notificationHelper.createNotification(notificationId!!, eventTitle!!, description!!).build()

        notificationHelper.getManager().notify(notificationId!!.toInt(), notification)


    }

}