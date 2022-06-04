package com.example.hcctv.view.fragment

import android.app.AlertDialog
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hcctv.R
import com.example.hcctv.adapter.CategoryItemAdapter
import com.example.hcctv.adapter.SummaryItemAdapter
import com.example.hcctv.base.BaseFragment
import com.example.hcctv.databinding.FragmentStatisticBinding
import com.example.hcctv.model.data.Category
import com.example.hcctv.model.data.Summary
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.github.mikephil.charting.utils.ColorTemplate
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class StatisticFragment(override val FRAGMENT_TAG: String = "StatisticFragment") :
    BaseFragment<FragmentStatisticBinding>(R.layout.fragment_statistic),
    OnChartValueSelectedListener {

    private val summaryAdapter by lazy {
        SummaryItemAdapter()
    }

    private val categoryAdapter by lazy {
        CategoryItemAdapter(itemClickListener = { category ->
            Log.d("hello", category.title)
            if (category.title == "배변") {
                activity.saveAndChangeFragment(CategoryDetailFragment())
            }
        })
    }

    private val summaryItemList = listOf(
        Summary("4월 1주", "(04.01 ~ 04.02)", 2),
        Summary("4월 2주", "(04.03 ~ 04.09)", 1),
        Summary("4월 3주", "(04.10 ~ 04.16)", 0),
        Summary("4월 4주", "(04.17 ~ 04.23)", 4),
        Summary("4월 5주", "(04.24 ~ 04.30)", 5)
    )

    private val categoryItemList = listOf(
        Category("배변", 80, 12, R.color.purple_200),
        Category("취침", 20, 3, androidx.appcompat.R.color.material_blue_grey_800)
    )

    private val selectItemList = listOf(
        "파이",
        "막대",
        "선"
    )

    private var state = false
    private var selected = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity.hideBottomNavi(true)

        initViews()
        bindViews()

        changeChartType()
        changeStatisticDate()
        showDayStatistic()
    }

    override fun onDestroy() {
        super.onDestroy()
        activity.hideBottomNavi(false)
    }

    private fun initViews() = with(binding) {
        activity.supportActionBar?.setDisplayHomeAsUpEnabled(true)

        summaryRecyclerView.adapter = summaryAdapter
        summaryRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        summaryAdapter.submitList(summaryItemList)

        categoryRecyclerView.adapter = categoryAdapter
        categoryRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        categoryAdapter.submitList(categoryItemList)

        barChart.visibility = android.view.View.INVISIBLE
        lineChart.visibility = android.view.View.INVISIBLE
        showPieGraph()
    }

    private fun bindViews() = with(binding) {
        topSummaryTotalView.text = "15회"
        totalCountTextView.text = "15회"

    }

    override fun onValueSelected(e: Entry?, h: Highlight?) {
        if (e == null) return
        else {
            Log.d("PieChart", e.x.toString() + e.y.toString())
        }
    }

    override fun onNothingSelected() {
        TODO("Not yet implemented")
    }

    private fun changeStatisticDate() {
        binding.btnChangeDate.setOnClickListener {
            val dateRangerPicker = MaterialDatePicker.Builder.dateRangePicker()
                .setTitleText("기간을 설정해주세요.")
                .build()

            dateRangerPicker.show(childFragmentManager, "data_picker")
            dateRangerPicker.addOnPositiveButtonClickListener {
                state = !state

                if (!state) {
                    yesData()
                } else {
                    noData()
                }

                object : MaterialPickerOnPositiveButtonClickListener<Pair<Long, Long>> {
                    override fun onPositiveButtonClick(selection: Pair<Long, Long>?) {
                        val calendar = Calendar.getInstance()
                        calendar.timeInMillis = selection?.first ?: 0
                        val startDate =
                            SimpleDateFormat("yyyyMMdd").format(calendar.time).toString()

                        calendar.timeInMillis = selection?.second ?: 0
                        val endDate =
                            SimpleDateFormat("yyyyMMdd").format(calendar.time).toString()
                    }
                }
            }
        }
    }

    private fun changeChartType() {
        binding.btnChangeType.setOnClickListener {
            AlertDialog
                .Builder(activity)
                .apply {
                    setTitle("그래프 표시 방식 설정")
                    setSingleChoiceItems(
                        selectItemList.toTypedArray(),
                        selected,
                        object : DialogInterface.OnClickListener {
                            override fun onClick(dialog: DialogInterface?, which: Int) {
                                when (which) {
                                    0 -> {
                                        changeShowType("Pie")
                                        selected = 0
                                    }
                                    1 -> {
                                        changeShowType("Bar")
                                        selected = 1
                                    }
                                    2 -> {
                                        changeShowType("Line")
                                        selected = 2
                                    }
                                }
                            }
                        })
                    setPositiveButton("확인", null)
                }.show()
        }
    }

    private fun showPieGraph() = with(binding) {
        val data = mutableListOf<PieEntry>()

        data.add(PieEntry(80f, "배변"))
        data.add(PieEntry(20f, "취침"))

        val dataSet = PieDataSet(data, null)
        dataSet.sliceSpace = 3f
        dataSet.selectionShift = 10f

        dataSet.colors = ColorTemplate.PASTEL_COLORS.toCollection(ArrayList())

        val pieData = PieData(dataSet)
        pieData.setValueFormatter(PercentFormatter())
        pieData.setValueTextColor(Color.WHITE)
        pieData.setValueTypeface(Typeface.MONOSPACE)
        pieData.setValueTextSize(10f)


        pieChart.data = pieData
        pieChart.description = null
        pieChart.animateXY(1400, 1400)
    }

    private fun showBarGraph() = with(binding) {
        val data = mutableListOf<BarEntry>()

        data.add(BarEntry(2f, 2f))
        data.add(BarEntry(1f, 1f))

        val dataSet = BarDataSet(data, null)

        dataSet.colors = ColorTemplate.PASTEL_COLORS.toCollection(ArrayList())

        val barData = BarData(dataSet)
        barData.setValueFormatter(PercentFormatter())
        barData.setValueTextColor(Color.WHITE)
        barData.setValueTypeface(Typeface.MONOSPACE)
        barData.setValueTextSize(10f)

        barChart.apply {
            setMaxVisibleValueCount(10)
            setDrawBarShadow(false)
            setDrawValueAboveBar(true)
            setPinchZoom(false)
            setDrawGridBackground(false)
            description.isEnabled = false

            xAxis.position = XAxis.XAxisPosition.BOTTOM
            xAxis.setDrawGridLines(false)
            xAxis.granularity = 1f

            axisLeft.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART)
            axisLeft.labelCount = 10
            axisLeft.spaceTop = 12f
            axisLeft.axisMaximum = 0f

            axisRight.setDrawGridLines(false)
            axisRight.labelCount = 10
            axisRight.spaceTop = 12f
            axisRight.axisMaximum = 0f
        }

        barChart.data = barData
        barChart.animateXY(1400, 1400)
    }

    private fun showLineGraph() = with(binding) {
        val data = mutableListOf<Entry>()

        data.add(Entry(80f, 80f))
        data.add(Entry(20f, 80f))

        val dataSet = LineDataSet(data, null)

        dataSet.colors = ColorTemplate.PASTEL_COLORS.toCollection(ArrayList())

        val lineData = LineData(dataSet)
        lineData.setValueFormatter(PercentFormatter())
        lineData.setValueTextColor(Color.WHITE)
        lineData.setValueTypeface(Typeface.MONOSPACE)
        lineData.setValueTextSize(10f)

        lineChart.apply {
            description = null
            isDragEnabled = false
            setPinchZoom(false)
            setScaleEnabled(false)
            xAxis.setDrawGridLines(false)
            xAxis.position = XAxis.XAxisPosition.BOTTOM
            axisRight.isEnabled = false
        }

        lineChart.data = lineData
        lineChart.description = null
        lineChart.animateXY(1400, 1400)
    }

    private fun showDayStatistic() = with(binding) {
        val data = mutableListOf<Entry>()

        forDayChart.apply {
            description = null
            isDragEnabled = false
            setPinchZoom(false)
            setScaleEnabled(false)
            xAxis.setDrawGridLines(false)
            xAxis.position = XAxis.XAxisPosition.BOTTOM
            axisRight.isEnabled = false
        }

        data.add(Entry(4f, 40f))
        data.add(Entry(0f, 30f))

        val dataSet = LineDataSet(data, null)
        dataSet.setCircleColor(R.color.teal_200)
        dataSet.color = R.color.teal_100

        dataSet.lineWidth = 1f
        dataSet.circleRadius = 3f

        dataSet.setDrawCircleHole(false)

        dataSet.valueTextSize = 9f
        dataSet.setDrawFilled(true)

        forDayChart.data = LineData(dataSet)
    }

    private fun changeShowType(type: String) = with(binding) {
        when (type) {
            "Pie" -> {
                barChart.visibility = View.INVISIBLE
                lineChart.visibility = View.INVISIBLE

                pieChart.visibility = View.VISIBLE
                showPieGraph()
            }
            "Bar" -> {
                pieChart.visibility = View.INVISIBLE
                lineChart.visibility = View.INVISIBLE

                barChart.visibility = View.VISIBLE
                showBarGraph()
            }
            "Line" -> {
                pieChart.visibility = View.INVISIBLE
                barChart.visibility = View.INVISIBLE

                lineChart.visibility = View.VISIBLE
                showLineGraph()
            }
        }
    }

    private fun noData() = with(binding) {
        group.visibility = View.GONE
        topSummaryTitleView.text = "5월 이벤트 발생 현황"
        topSummaryTotalView.text = "0회"
        noEventTextView.visibility = View.VISIBLE
    }

    private fun yesData() = with(binding) {
        group.visibility = View.VISIBLE
        noEventTextView.visibility = View.GONE
        barChart.visibility = View.INVISIBLE
        lineChart.visibility = View.INVISIBLE
        topSummaryTitleView.text = "4월 이벤트 발생 현황"
        topSummaryTotalView.text = "15회"
    }

}