package com.example.hcctv.base

import android.os.Bundle
import android.util.Log
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BaseActivity<T : ViewDataBinding>(@LayoutRes private val layoutResId: Int) :
    AppCompatActivity() {
    abstract val ACTIVITY_TAG: String

    private var _binding: T? = null
    val binding get() = _binding ?: error("Not Initialized")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(ACTIVITY_TAG, "onCreate")
        _binding = DataBindingUtil.setContentView(this, layoutResId)
        binding.lifecycleOwner = this@BaseActivity
    }

    override fun onStart() {
        Log.i(ACTIVITY_TAG, "onStart")
        super.onStart()
    }

    override fun onRestart() {
        Log.i(ACTIVITY_TAG, "onRestart")
        super.onRestart()
    }

    override fun onResume() {
        Log.i(ACTIVITY_TAG, "onResume")
        super.onResume()
    }

    override fun onPause() {
        Log.i(ACTIVITY_TAG, "onPause")
        super.onPause()
    }

    override fun onStop() {
        Log.i(ACTIVITY_TAG, "onStop")
        super.onStop()
    }

    override fun onDestroy() {
        Log.i(ACTIVITY_TAG, "onDestroy")
        super.onDestroy()
    }
}