package com.example.hcctv.view.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hcctv.R
import com.example.hcctv.adapter.DeviceItemAdapter
import com.example.hcctv.base.BaseFragment
import com.example.hcctv.databinding.FragmentDeviceBinding
import com.example.hcctv.viewmodel.DeviceViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DeviceListFragment(override val FRAGMENT_TAG: String = "DeviceFragment") :
    BaseFragment<FragmentDeviceBinding>(R.layout.fragment_device) {

    private val adapter by lazy {
        DeviceItemAdapter(itemClickListener = {
            val fragment = DeviceDetailFragment()
            val bundle = Bundle().apply {
                putString("id", it.id.toString())
            }
            fragment.arguments = bundle

            activity.saveAndChangeFragment(fragment)
        })
    }

    private val viewModel by lazy {
        ViewModelProvider(this)[DeviceViewModel::class.java]
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindAdapter()
        bindViews()
        subscribeObservers()
        addDevice()
        moveStatistic()
    }

    private fun bindViews() {
        binding.btnAddDevice.setOnClickListener {
            BottomSheetBehavior.from(binding.bottomSheet).state =
                BottomSheetBehavior.STATE_EXPANDED
        }
    }

    private fun bindAdapter() {
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun subscribeObservers() {
        viewModel.getAllDevices().observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }

    private fun addDevice() {
        binding.btnAdd.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                viewModel.insertDevice(binding.addressEditText.text.toString())
                withContext(Dispatchers.Main) {
                    BottomSheetBehavior.from(binding.bottomSheet).state =
                        BottomSheetBehavior.STATE_HIDDEN
                }
            }
        }
    }

    private fun moveStatistic() {
//        binding.btnMove.setOnClickListener {
//            activity.saveAndChangeFragment(StatisticFragment())
//        }
    }
}