package com.example.reminder.notify

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.PowerManager


class AlertReceiver: BroadcastReceiver(){

    private lateinit var notificationHelper: NotificationHelper


    @SuppressLint("InvalidWakeLockTag")
    override fun onReceive(context: Context?, intent: Intent?) {

        // wake the screen after receiving the notification
        val pm = context!!.getSystemService(Context.POWER_SERVICE) as PowerManager
        val isScreenOn = pm.isScreenOn
        if (!isScreenOn) {
            val wl = pm.newWakeLock(
                PowerManager.FULL_WAKE_LOCK or PowerManager.ACQUIRE_CAUSES_WAKEUP or PowerManager.ON_AFTER_RELEASE,
                "MyLock"
            )
            wl.acquire(10000)
            val wl_cpu = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "MyCpuLock")
            wl_cpu.acquire(10000)
        }


        notificationHelper = NotificationHelper(context)

        val eventTitle = intent?.getStringExtra("title")
        val notificationId = intent?.getLongExtra("id", -1)
        val description = intent?.getStringExtra("description")

        // in createNotification(string) function we give the event title as a parameter
        val notification = notificationHelper.createNotification(
            notificationId!!,
            eventTitle!!,
            description!!
        ).build()

        notificationHelper.getManager().notify(notificationId!!.toInt(), notification)


    }

}