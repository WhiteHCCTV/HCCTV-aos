package com.example.hcctv.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.hcctv.databinding.ItemCategoryDetailBinding
import com.example.hcctv.model.data.CategoryDetail

class CategoryDetailItemAdapter : RecyclerView.Adapter<CategoryDetailItemAdapter.ViewHolder>() {
    private var categoryDetailItemList: List<CategoryDetail>? = null

    inner class ViewHolder(private val binding: ItemCategoryDetailBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindViews(item: CategoryDetail) = with(binding) {
            binding.dateTextView.text = item.date
            binding.titleTextView.text = item.title
            binding.countTextView.text = "${item.count}회"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemCategoryDetailBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        categoryDetailItemList?.let {
            holder.bindViews(it[position])
        }
    }

    override fun getItemCount(): Int {
        return categoryDetailItemList?.size ?: 0
    }

    fun submitList(items: List<CategoryDetail>?) {
        categoryDetailItemList = items
        notifyDataSetChanged()
    }
}