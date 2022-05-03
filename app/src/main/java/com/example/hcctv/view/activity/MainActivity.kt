package com.example.hcctv.view.activity

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.hcctv.R
import com.example.hcctv.base.BaseActivity
import com.example.hcctv.databinding.ActivityMainBinding
import com.example.hcctv.view.fragment.DeviceListFragment
import com.example.hcctv.view.fragment.HomeFragment
import com.example.hcctv.view.fragment.MyPageFragment

class MainActivity(override val ACTIVITY_TAG: String = "MAIN_ACTIVITY") :
    BaseActivity<ActivityMainBinding>(R.layout.activity_main) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bindViews()
    }

    private fun bindViews() {
        binding.bottomNavigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home -> {
                    changeFragment(HomeFragment())
                }
                R.id.device -> {
                    changeFragment(DeviceListFragment())
                }
                R.id.mypage -> {
                    changeFragment(MyPageFragment())
                }
            }
            return@setOnItemSelectedListener true
        }
    }

    private fun changeFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(binding.fragmentContainer.id, fragment)
            .commit()
    }
}