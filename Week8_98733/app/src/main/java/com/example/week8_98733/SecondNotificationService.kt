package com.example.week8_98733

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.CountDownTimer
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.lifecycle.MutableLiveData

class SecondNotificationService : Service() {

    private lateinit var notificationManager: NotificationManager
    private lateinit var notificationBuilder: NotificationCompat.Builder
    private lateinit var countDownTimer: CountDownTimer

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val channelId = intent?.getStringExtra(EXTRA_ID) ?: ""
        startForegroundService(channelId)
        return START_NOT_STICKY
    }

    private fun startForegroundService(channelId: String) {
        notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Second Notification Service",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }

        notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setContentTitle("Second Countdown")
            .setContentText("00:10")
            .setSmallIcon(R.mipmap.ic_launcher)
            .setOnlyAlertOnce(true)

        startForeground(2, notificationBuilder.build())

        countDownTimer = object : CountDownTimer(10000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                notificationBuilder.setContentText("00:${(millisUntilFinished / 1000).toString().padStart(2, '0')}")
                notificationManager.notify(2, notificationBuilder.build())
            }

            override fun onFinish() {
                trackingCompletion.postValue(channelId)
                stopForeground(STOP_FOREGROUND_REMOVE)
                stopSelf()
            }
        }.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        countDownTimer.cancel()
    }

    companion object {
        const val EXTRA_ID = "EXTRA_ID"
        val trackingCompletion = MutableLiveData<String>()
    }
}