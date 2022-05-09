package com.example.hcctv.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.example.hcctv.view.activity.MainActivity

abstract class BaseFragment<T : ViewDataBinding>(@LayoutRes private val layoutResId: Int) :
    Fragment() {
    abstract val FRAGMENT_TAG: String

    private var _binding: T? = null
    val binding get() = _binding ?: error("Not Initialized")

    private val _activity by lazy {
        requireActivity() as MainActivity
    }
    val activity get() = _activity

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, layoutResId, container, false)
        binding.lifecycleOwner = this@BaseFragment
        return binding.root
    }
}