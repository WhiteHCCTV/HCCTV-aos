package com.example.hcctv.view.fragment

import android.app.AlertDialog
import android.content.DialogInterface
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Environment
import android.os.Environment.DIRECTORY_DCIM
import android.provider.MediaStore
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.hcctv.R
import com.example.hcctv.adapter.ImageItemAdapter
import com.example.hcctv.base.BaseFragment
import com.example.hcctv.databinding.FragmentDeviceDetailBinding
import com.example.hcctv.viewmodel.DeviceDetailViewModel
import kotlinx.coroutines.*
import java.io.*
import java.net.Socket
import java.net.UnknownHostException

class DeviceDetailFragment(override val FRAGMENT_TAG: String = "DEVICE_DETAIL_FRAGMENT") :
    BaseFragment<FragmentDeviceDetailBinding>(R.layout.fragment_device_detail) {
    private val imageItemList: MutableList<Bitmap> = mutableListOf()
    private var selectedItemIndex: MutableList<Boolean> = mutableListOf()
    private var fileCount = 0
    private var deviceId = -1
    private var socket: Socket? = null

    private val adapter by lazy {
        ImageItemAdapter()
    }

    private val viewModel by lazy {
        ViewModelProvider(this)[DeviceDetailViewModel::class.java]
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity.hideBottomNavi(true)

        initViews()
        saveSelectedItems()
    }

    override fun onDestroy() {
        super.onDestroy()
        socket?.close()

        activity.hideBottomNavi(false)
    }

    private fun initViews() {
        val bundle = arguments

        if (bundle != null) {
            deviceId = bundle.getString("id")!!.toInt()
        }

        getFiles()
        initRecyclerView()
    }

    private fun initRecyclerView() {
        binding.imageRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.imageRecyclerView.adapter = adapter
    }


    // Socket을 사용하기 위해 IO Thread로 변경
    private fun getFiles() {
        CoroutineScope(Dispatchers.IO).launch {
            getImageCount()
            getImages()
        }
    }


    // 서버에서 이미지를 가져오는 메소드
    private suspend fun getImageCount() {
        try {
            val initSocket = Socket(IP_ADDRESS, PORT_NUMBER)
            val inputStream = initSocket.getInputStream()
            val dataInputStream = DataInputStream(inputStream)
            val imageCount = dataInputStream.readInt()

            // 서버로부터 받아온 이미지 개수를 내장 스토리지에 저장
            try {
                viewModel.insertImageCount(deviceId, imageCount)
                // 이미지 개수만큼 이미지를 가져오기 위해 변수에 저장
                fileCount = viewModel.getImageCount(deviceId)
            } catch (e: Exception) {
                e.printStackTrace()
            }

            dataInputStream.close()
            inputStream.close()
            initSocket.close()

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    // 서버에서 이미지를 가져오는 메소드
    private fun getImages() {
        // 이미지 수신
        var count = 0
        var fileOutputStream: FileOutputStream? = null

        do {
            try {
                socket = Socket(IP_ADDRESS, PORT_NUMBER)
                val inputStream = socket?.getInputStream()
                val bitmap = BitmapFactory.decodeStream(inputStream)
                imageItemList.add(bitmap)
            } catch (e: UnknownHostException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                fileOutputStream?.let {
                    try {
                        it.close()
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
                socket?.let {
                    try {
                        it.close()
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
            }
            count += 1
        } while (count < fileCount)

        // 리사이클러뷰에서 UI를 변경해줘야하므로 Main Thread로 변경
        CoroutineScope(Dispatchers.Main).launch {
            adapter.submitList(imageItemList)
        }
    }

    private fun sendClickedImages(items: List<Boolean>) {
        val unSelectedItems = mutableListOf<Int>()

        for (i in items.indices) {
            if (!items[i]) unSelectedItems.add(i + 1)
        }

        var socket: Socket? = null
        var objectOutputStream: ObjectOutputStream? = null

        try {
            socket = Socket(IP_ADDRESS, PORT_NUMBER)
            objectOutputStream = ObjectOutputStream(socket.getOutputStream())

            objectOutputStream.writeObject(unSelectedItems)
        } catch (e: UnknownHostException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            socket?.close()
            objectOutputStream?.close()
        }
    }

    private fun saveSelectedItems() {
        binding.btnSubmit.setOnClickListener {
            val listener = DialogInterface.OnClickListener { _, which ->
                when (which) {
                    DialogInterface.BUTTON_POSITIVE -> {
                        CoroutineScope(Dispatchers.IO).launch {
                            sendClickedImages(adapter.getList())
                            activity.supportFragmentManager.popBackStack()
                        }
                    }
                    DialogInterface.BUTTON_NEGATIVE -> Unit
                }
            }

            AlertDialog.Builder(requireActivity()).apply {
                setTitle("학습 사진 업로드")
                setMessage("업로드 하시겠습니까?")
                setPositiveButton("예", listener)
                setNegativeButton("아니오", listener)
                show()
            }
        }
    }

    private fun showProgress() = with(binding) {
        progressBar.visibility = View.VISIBLE
        imageRecyclerView.visibility = View.INVISIBLE
        btnSubmit.visibility = View.INVISIBLE
    }

    private fun hideProgress() = with(binding) {
        progressBar.visibility = View.INVISIBLE
        imageRecyclerView.visibility = View.VISIBLE
        btnSubmit.visibility = View.VISIBLE
    }

    private fun showErrorMessage() = with(binding) {
        titleTextView.visibility = View.GONE
        subTitleTextView.visibility = View.GONE
        imageRecyclerView.visibility = View.GONE
        btnSubmit.visibility = View.GONE

        errorTitleTextView.visibility = View.VISIBLE
        errorSubtitleTextView.visibility = View.VISIBLE
    }

    companion object {
        const val IP_ADDRESS = "168.188.128.94"
        const val PORT_NUMBER: Int = 8080
        const val PATH = "./sample/server"
    }
}