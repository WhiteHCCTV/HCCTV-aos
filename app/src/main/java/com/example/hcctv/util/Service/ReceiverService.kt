package com.example.hcctv.util.Service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.os.BuildCompat
import com.example.hcctv.R
import com.example.hcctv.view.fragment.DeviceDetailFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.InputStream
import java.net.Socket

class ReceiverService : Service() {
    override fun onBind(intent: Intent?): IBinder? {
        TODO("Not yet implemented")
    }

    override fun onCreate() {
        super.onCreate()

        connectSocket()
        receiveEvent()
    }

    private fun connectSocket() {
        var socket: Socket? = null
        var inputStream: InputStream? = null

        CoroutineScope(Dispatchers.IO).launch {
            try {
                socket = Socket(DeviceDetailFragment.IP_ADDRESS, DeviceDetailFragment.PORT_NUMBER)
                while (true) {
                    inputStream = socket?.getInputStream()
                    delay(5000)
                    Log.d("hello", "hello")
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                socket?.close()
            }
        }

    }

    private fun receiveEvent() {
        val builder = NotificationCompat.Builder(this@ReceiverService, RECEIVE_EVENT)
            .setSmallIcon(R.drawable.ic__113447_poop_solid_icon)
            .setContentTitle("이벤트 수신 중")
            .setContentText("이벤트 수신을 위해 백그라운드에서 작동 중입니다.")
            .setPriority(NotificationCompat.PRIORITY_HIGH)

        // Android 26 이상부터는 NotificationChannel 등록 필요
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(RECEIVE_EVENT, "이벤트 발생", NotificationManager.IMPORTANCE_HIGH)
            val manager =
                applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }

        startForeground(2, builder.build())
    }

    // 이벤트를 수신했을 경우 사용자에게 알림 전송
    // TODO 이벤트 페이지로 이동하는 것 구현 필요
    private fun check() {
        val builder = NotificationCompat.Builder(this@ReceiverService, RECEIVE_EVENT)
            .setSmallIcon(R.drawable.ic__113447_poop_solid_icon)
            .setContentTitle("이벤트 발생")
            .setContentText("Device 1에서 배변 이벤트가 발생했습니다.")
            .setPriority(NotificationCompat.PRIORITY_HIGH)

        // Android 26 이상부터는 NotificationChannel 등록 필요
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(RECEIVE_EVENT, "이벤트 발생", NotificationManager.IMPORTANCE_HIGH)
            val manager =
                applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }

        NotificationManagerCompat.from(this@ReceiverService)
            .notify(1, builder.build())
    }

    companion object {
        const val NOTIFICATION_ID = 9999
        const val RECEIVE_EVENT = "receive_event"
        const val CHANNEL_ID = "socket_channel"
    }


}