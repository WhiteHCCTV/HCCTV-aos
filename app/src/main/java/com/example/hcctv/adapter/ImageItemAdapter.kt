package com.example.hcctv.adapter

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.hcctv.R
import com.example.hcctv.databinding.ItemImageBinding

class ImageItemAdapter :
    RecyclerView.Adapter<ImageItemAdapter.ViewHolder>() {
    private var imageItemList: List<Bitmap>? = null
    private var selectItemList: ArrayList<Boolean> = arrayListOf(
        false,
        false,
        false,
        false,
        false
    )

    inner class ViewHolder(private val binding: ItemImageBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun initViews(item: Bitmap) {
            Glide.with(binding.imageView)
                .load(item)
                .centerCrop()
                .into(binding.imageView)

            binding.root.setOnClickListener {
                if (!selectItemList[position]) {
                    binding.imageView.setPadding(10, 10, 10, 10)
                    binding.imageView.setBackgroundResource(R.drawable.image_selected_background)
                    selectItemList[position] = true
                } else {
                    binding.imageView.setPadding(0, 0, 0, 0)
                    binding.imageView.setBackgroundResource(0)
                    selectItemList[position] = false
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

    fun submitList(items: List<Bitmap>?) {
        imageItemList = items
        notifyDataSetChanged()
    }

    fun getList(): List<Boolean> {
        return selectItemList
    }

}