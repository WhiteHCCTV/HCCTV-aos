package com.example.hcctv.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.setPadding
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.hcctv.R
import com.example.hcctv.databinding.ItemImageBinding
import com.example.hcctv.model.data.Image

class ImageItemAdapter(private val itemClickListener: (Int) -> Unit) :
    RecyclerView.Adapter<ImageItemAdapter.ViewHolder>() {
    private var imageItemList: List<Image>? = null

    inner class ViewHolder(private val binding: ItemImageBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun initViews(item: Image) {
            Glide.with(binding.imageView)
                .load(item.image)
                .centerCrop()
                .into(binding.imageView)

            binding.root.setOnClickListener {
                itemClickListener(adapterPosition)

                if (item.selected == false) {
                    binding.imageView.setPadding(10)
                    binding.imageView.setBackgroundResource(R.drawable.image_selected_background)
                    item.selected = true
                } else {
                    binding.imageView.setPadding(0)
                    binding.imageView.setBackgroundResource(0)
                    item.selected = false
                }
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemImageBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        imageItemList?.let {
            holder.initViews(it[position])
        }
    }

    override fun getItemCount(): Int {
        return imageItemList?.size ?: 0
    }

    fun submitList(items: List<Image>?) {
        imageItemList = items
        notifyDataSetChanged()
    }
}