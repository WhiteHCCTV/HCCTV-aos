package com.example.hcctv.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.hcctv.databinding.ItemCategoryBinding
import com.example.hcctv.model.data.Category

class CategoryItemAdapter(private val itemClickListener : (Category) -> Unit) : RecyclerView.Adapter<CategoryItemAdapter.ViewHolder>() {
    private var categoryItemList: List<Category>? = null

    inner class ViewHolder(private val binding: ItemCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindViews(item: Category) = with(binding) {
            binding.categoryColor.setBackgroundColor(item.color)
            binding.firstTextView.text = item.title
            binding.secondTextView.text = "${item.percent}%"
            binding.thirdTextView.text = "${item.count}회"

            fourthView.setOnClickListener {
                itemClickListener(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        categoryItemList?.let {
            holder.bindViews(it[position])
        }
    }

    override fun getItemCount(): Int {
        return categoryItemList?.size ?: 0
    }

    fun submitList(items: List<Category>?) {
        categoryItemList = items
        notifyDataSetChanged()
    }
}