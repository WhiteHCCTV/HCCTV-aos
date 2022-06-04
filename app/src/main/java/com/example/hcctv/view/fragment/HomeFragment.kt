package com.example.hcctv.view.fragment

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.hcctv.R
import com.example.hcctv.base.BaseFragment
import com.example.hcctv.databinding.FragmentHomeBinding
import com.example.hcctv.util.Service.ReceiverService

class HomeFragment(override val FRAGMENT_TAG: String = "HomeFragment") :
    BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViews()
    }

    private fun bindViews() = with(binding) {
        btnEvent.setOnClickListener {
            // TODO 알림 선택하면 이벤트에 대한 사진 확인하는 기능 구현 필요
            val builder = NotificationCompat.Builder(
                activity,
                "HI"
            )
                .setSmallIcon(R.drawable.ic__113447_poop_solid_icon)
                .setContentTitle("배변 이벤트 발생")
                .setContentText("Device 1에서 배변 이벤트가 발생했습니다.")
                .setPriority(NotificationCompat.PRIORITY_HIGH)

            // Android 26 이상부터는 NotificationChannel 등록 필요
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val channel = NotificationChannel(
                    "HI",
                    "이벤트 발생",
                    NotificationManager.IMPORTANCE_HIGH
                )
                val manager =
                    activity.applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                manager.createNotificationChannel(channel)
            }

            // notify의 id는 알림 삭제를 위한 식별자
            NotificationManagerCompat.from(activity)
                .notify(1, builder.build())

        }
    }
}