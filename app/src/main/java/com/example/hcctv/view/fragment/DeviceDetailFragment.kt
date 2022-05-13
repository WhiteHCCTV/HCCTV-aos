package com.example.hcctv.view.fragment

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.example.hcctv.R
import com.example.hcctv.adapter.ImageItemAdapter
import com.example.hcctv.base.BaseFragment
import com.example.hcctv.databinding.FragmentDeviceDetailBinding
import com.example.hcctv.model.data.Image
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.DataInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.lang.Exception
import java.net.Socket
import java.net.UnknownHostException

class DeviceDetailFragment(override val FRAGMENT_TAG: String = "DEVICE_DETAIL_FRAGMENT") :
    BaseFragment<FragmentDeviceDetailBinding>(R.layout.fragment_device_detail) {
    private val imageItemList: MutableList<Image> = mutableListOf()
    private val selectedItemIndex: MutableList<Int> = mutableListOf()

    private val adapter by lazy {
        ImageItemAdapter(itemClickListener = {
            if (selectedItemIndex.contains(it)) {
                selectedItemIndex.remove(it)
            } else {
                selectedItemIndex.add(it)
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity.hideBottomNavi(true)

        initViews()
        saveSelectedItems()
    }

    override fun onDestroy() {
        super.onDestroy()

        activity.hideBottomNavi(false)
    }

    private fun initViews() {
        getFiles()
        initRecyclerView()
    }

    private fun initRecyclerView() {
        binding.imageRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.imageRecyclerView.adapter = adapter
        adapter.submitList(imageItemList.toList())
    }

    private fun getFileCount(): Int {
        var fileCount = 0

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val initSocket = Socket(IP_ADDRESS, PORT_NUMBER)
                val inputStream = initSocket.getInputStream()
                val dataInputStream = DataInputStream(inputStream)

                fileCount = dataInputStream.readInt()

                // UI 변경을 위해 메인 쓰레드로 전환
                withContext(Dispatchers.Main) {
                    activity.showToast(requireContext(), fileCount.toString())
                }

                dataInputStream.close()
                inputStream.close()
                initSocket.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        return fileCount
    }

    private fun getFiles() {
        // Progress 보여주고 이미지 수신 완료되면 RecyclerView 보여주기
        showProgress()

        // 이미지 수신
        val fileCount = getFileCount()
        var count = 0

        var socket: Socket? = null
        var fileOutputStream: FileOutputStream? = null

        CoroutineScope(Dispatchers.IO).launch {
            do {
                try {
                    socket = Socket(IP_ADDRESS, PORT_NUMBER)
                    val inputStream = socket?.getInputStream()
                    val filePath = "$PATH$count.jpg"
                    val fileOutputStream = FileOutputStream(filePath)
                    val dataBuffer = ByteArray(10000)
                    val dataLength = inputStream?.read(dataBuffer)

                    while (dataLength != -1) {
                        // dataBuffer에 파일 쓰고
                        fileOutputStream.write(dataBuffer, 0, dataLength!!)
                        // dataBuffer 저장
                        imageItemList.add(Image(id = count, image = dataBuffer, selected = false))
                    }


                    withContext(Dispatchers.Main) {
                        activity.showToast(requireContext(), filePath)
                    }

                } catch (e: UnknownHostException) {
                    e.printStackTrace()
                } catch (e: IOException) {
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
        }

        hideProgress()
    }

    private fun saveSelectedItems() {
        binding.btnSubmit.setOnClickListener {
            val listener = DialogInterface.OnClickListener { _, which ->
                when (which) {
                    DialogInterface.BUTTON_POSITIVE -> {
                        // 오름차순으로 정렬하고 submit
                        selectedItemIndex.sort()
                    }
                    DialogInterface.BUTTON_NEGATIVE -> {

                    }
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

    companion object {
        const val IP_ADDRESS = "http://192.168.1.1"
        const val PORT_NUMBER: Int = 5555
        const val PATH = "./sample/client"
    }
}