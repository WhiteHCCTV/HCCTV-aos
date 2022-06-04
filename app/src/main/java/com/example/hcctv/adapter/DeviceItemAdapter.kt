package com.example.hcctv.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.hcctv.databinding.ItemDeviceBinding
import com.example.hcctv.model.data.Device

class DeviceItemAdapter(private val itemClickListener: (Device) -> Unit) :
    RecyclerView.Adapter<DeviceItemAdapter.ViewHolder>() {
    private var deviceItemList: List<Device>? = null

    inner class ViewHolder(private val binding: ItemDeviceBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindViews(item: Device) {
            binding.addressTextView.text = item.address

            binding.root.setOnClickListener {
                itemClickListener(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemDeviceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        deviceItemList?.let {
            holder.bindViews(it[position])
        }
    }

    override fun getItemCount(): Int {
        return deviceItemList?.size ?: 0
    }

    fun submitList(items: List<Device>?) {
        deviceItemList = items
        notifyDataSetChanged()
    }
}