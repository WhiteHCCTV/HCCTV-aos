package com.example.hcctv.view.fragment

import android.os.Bundle
import android.view.View
import com.example.hcctv.R
import com.example.hcctv.base.BaseFragment
import com.example.hcctv.databinding.FragmentStatisticBinding

class StatisticFragment(override val FRAGMENT_TAG: String = "StatisticFragment") :
    BaseFragment<FragmentStatisticBinding>(R.layout.fragment_statistic) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity.hideBottomNavi(true)
    }

    override fun onDestroy() {
        super.onDestroy()
        activity.hideBottomNavi(false)
    }
}