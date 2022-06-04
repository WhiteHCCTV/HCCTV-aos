package com.example.hcctv.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.hcctv.databinding.ItemSummaryBinding
import com.example.hcctv.model.data.Summary

class SummaryItemAdapter : RecyclerView.Adapter<SummaryItemAdapter.ViewHolder>() {
    private var summaryItemList: List<Summary>? = null

    inner class ViewHolder(private val binding: ItemSummaryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindViews(item: Summary) {
            binding.firstTextView.text = item.title
            binding.secondTextView.text = item.date
            binding.thirdTextView.text = "${item.count}회"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemSummaryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        summaryItemList?.let {
            holder.bindViews(it[position])
        }
    }

    override fun getItemCount(): Int {
        return summaryItemList?.size ?: 0
    }

    fun submitList(items: List<Summary>?) {
        summaryItemList = items
        notifyDataSetChanged()
    }
}