package com.example.hcctv.view.fragment

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hcctv.R
import com.example.hcctv.adapter.DeviceItemAdapter
import com.example.hcctv.base.BaseFragment
import com.example.hcctv.databinding.FragmentDeviceBinding
import com.example.hcctv.viewmodel.DeviceViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior

class DeviceListFragment(override val FRAGMENT_TAG: String = "DeviceFragment") :
    BaseFragment<FragmentDeviceBinding>(R.layout.fragment_device) {

    private val adapter by lazy {
        DeviceItemAdapter(itemClickListener = {
            activity.saveAndChangeFragment(DeviceDetailFragment())
        })
    }

    private val deviceViewModel by lazy {
        ViewModelProvider(
            this,
            DeviceViewModel.Factory(requireActivity().application)
        )[DeviceViewModel::class.java]
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
        deviceViewModel.deviceItemList.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }

    private fun addDevice() {
        binding.btnAdd.setOnClickListener {
            deviceViewModel.insertDevice(binding.addressEditText.text.toString())
            BottomSheetBehavior.from(binding.bottomSheet).state = BottomSheetBehavior.STATE_HIDDEN
        }
    }

    private fun moveStatistic() {
        binding.btnMove.setOnClickListener {
            activity.saveAndChangeFragment(StatisticFragment())
        }
    }

    override fun onResume() {
        super.onResume()

        deviceViewModel.getDeviceItems()
    }
}