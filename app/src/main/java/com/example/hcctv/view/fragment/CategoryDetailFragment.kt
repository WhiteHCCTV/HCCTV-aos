package com.example.hcctv.view.fragment

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hcctv.R
import com.example.hcctv.adapter.CategoryDetailItemAdapter
import com.example.hcctv.base.BaseFragment
import com.example.hcctv.databinding.FragmentCategoryDetailBinding
import com.example.hcctv.model.data.CategoryDetail

class CategoryDetailFragment(override val FRAGMENT_TAG: String = "CategoryDetailFragment") :
    BaseFragment<FragmentCategoryDetailBinding>(R.layout.fragment_category_detail) {
    private val categoryDetailItemList = listOf(
        CategoryDetail("배변", "2022년 4월 27일", 2),
        CategoryDetail("배변", "2022년 4월 25일", 2),
        CategoryDetail("배변", "2022년 4월 24일", 1),
        CategoryDetail("배변", "2022년 4월 22일", 2),
        CategoryDetail("배변", "2022년 4월 21일", 1),
        CategoryDetail("배변", "2022년 4월 17일", 1),
        CategoryDetail("배변", "2022년 4월 15일", 2),
        CategoryDetail("배변", "2022년 4월 14일", 1),
        )

    private val adapter by lazy {
        CategoryDetailItemAdapter()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() = with(binding) {
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(activity)

        adapter.submitList(categoryDetailItemList)
    }
}