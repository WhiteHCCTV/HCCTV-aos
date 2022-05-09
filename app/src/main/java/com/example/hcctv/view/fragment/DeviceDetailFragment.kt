package com.example.hcctv.view.fragment

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.hcctv.R
import com.example.hcctv.base.BaseFragment
import com.example.hcctv.databinding.FragmentDeviceDetailBinding
import io.socket.client.IO
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class DeviceDetailFragment(override val FRAGMENT_TAG: String = "DEVICE_DETAIL_FRAGMENT") :
    BaseFragment<FragmentDeviceDetailBinding>(R.layout.fragment_device_detail) {

    private val socket by lazy {
        IO.socket(ipAddress)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity.hideBottomNavi(true)

        connectSocket()
        sendMessage()
        receiveMessage()
    }

    override fun onDestroy() {
        super.onDestroy()

        activity.hideBottomNavi(false)
        socket.disconnect()
    }

    private fun connectSocket() {
        try {
            socket.connect()
            Toast.makeText(getActivity(), "소켓 연결 완료", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun sendMessage() {
        binding.btnSend.setOnClickListener {
            val message = binding.messageEditText.text.toString().trim()
            binding.messageEditText.text = null

            socket.emit("new message", message)

            Toast.makeText(getActivity(), "전송 완료", Toast.LENGTH_SHORT).show()
        }
    }

    private fun receiveMessage() {
        Toast.makeText(getActivity(), "대기 중", Toast.LENGTH_SHORT).show()
        try {
            socket.on("new message") { args ->
                Toast.makeText(getActivity(), "수신 중", Toast.LENGTH_SHORT).show()
                CoroutineScope(Dispatchers.Main).launch {
                    val data = args[0]
                    var message = ""
                    try {
                        message = data.toString()
                    } catch (e: Error) {
                        e.printStackTrace()
                    }
                    Toast.makeText(getActivity(), "수신 완료\n$message", Toast.LENGTH_SHORT).show()
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    companion object {
        const val ipAddress = "http://192.168.1.1:8000"
    }
}