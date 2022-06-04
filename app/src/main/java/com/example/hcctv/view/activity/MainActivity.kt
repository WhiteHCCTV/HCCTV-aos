package com.example.hcctv.view.activity

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.example.hcctv.R
import com.example.hcctv.base.BaseActivity
import com.example.hcctv.databinding.ActivityMainBinding
import com.example.hcctv.util.Service.ReceiverService
import com.example.hcctv.view.fragment.DeviceListFragment
import com.example.hcctv.view.fragment.HomeFragment
import com.example.hcctv.view.fragment.MyPageFragment
import com.example.hcctv.view.fragment.StatisticFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.Socket

class MainActivity(override val ACTIVITY_TAG: String = "MAIN_ACTIVITY") :
    BaseActivity<ActivityMainBinding>(R.layout.activity_main) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestPermissions()
        initViews()
        bindViews()
    }

    override fun onDestroy() {
        super.onDestroy()
        val intent = Intent(this@MainActivity, ReceiverService::class.java)
        // baseContext.stopService(intent)
    }

    private fun initViews() {
        val intent = Intent(this@MainActivity, ReceiverService::class.java)
       //  baseContext.startService(intent)
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
                R.id.statistic -> {
                    changeFragment(StatisticFragment())
                }
                R.id.mypage -> {
                    changeFragment(MyPageFragment())
                }
            }
            return@setOnItemSelectedListener true
        }
    }

    fun changeFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(binding.fragmentContainer.id, fragment)
            .commit()
    }

    fun saveAndChangeFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(binding.fragmentContainer.id, fragment)
            .addToBackStack(null)
            .commit()
    }

    fun savaAndTransmitFragment(fragment: Fragment, bundle: Bundle) {
        fragment.arguments = bundle

        supportFragmentManager.beginTransaction()
            .replace(binding.fragmentContainer.id, fragment)
            .addToBackStack(null)
            .commit()
    }

    fun hideBottomNavi(state: Boolean) {
        if (state) binding.bottomNavigation.visibility = View.GONE
        else binding.bottomNavigation.visibility = View.VISIBLE
    }

    fun showToast(context: Context, text: String) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(this, permissions, REQUEST_CODE_EXTERNAL)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_EXTERNAL && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
        } else {
            finish()
        }
    }

    companion object {
        val permissions = arrayOf(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )

        const val REQUEST_CODE_EXTERNAL = 1
    }
}