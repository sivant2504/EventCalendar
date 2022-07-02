package com.sivan.calendar.fragment

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.sivan.calendar.databinding.CalendarWidgetBinding
import com.sivan.calendar.databinding.DayCellLayoutBinding
import java.util.*
import kotlin.collections.ArrayList

class CalendarFragment : Fragment() {
    private lateinit var mBinding: CalendarWidgetBinding
    private lateinit var mAdapter: CalendarFragmentAdapter
    private val months = ArrayList<Calendar>()
    private var pageNumbers = 25
    private val mCenterPosition = (pageNumbers - 1) / 2

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = CalendarWidgetBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        FragmentAttributes(requireContext())
        initHeader()
        mAdapter = CalendarFragmentAdapter(childFragmentManager, lifecycle, months)
        resetMonth()
        mBinding.calendarViewPager.apply {
            offscreenPageLimit = 3
            adapter = mAdapter
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {

                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    onSelectMonth(months[position])
                }
            })
            setCurrentItem(mCenterPosition, false)
        }
    }

    var month: Calendar
        get() {
            return months[mBinding.calendarViewPager.currentItem]
        }
        set(month) {
            resetMonth(month)
            onSelectMonth(month)
            mBinding.calendarViewPager.setCurrentItem(mCenterPosition, true)
        }

    private fun initHeader() {
        mBinding.currentMonthLabel.setOnClickListener { showDatePicker() }
        mBinding.previousButton.setOnClickListener {
            mBinding.calendarViewPager.apply { post { setCurrentItem(currentItem - 1, true) } }
        }
        mBinding.forwardButton.setOnClickListener {
            mBinding.calendarViewPager.apply { post { setCurrentItem(currentItem + 1, true) } }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun resetMonth(month: Calendar = Calendar.getInstance()) {
        month.setMidnight()
        months.clear()
        for (i in 0 until pageNumbers) {
            months.add(
                month.copy().apply { add(Calendar.MONTH, i - mCenterPosition) })
        }
        mAdapter.notifyDataSetChanged()
    }

    private fun showDatePicker() {
        DatePickerDialog(
            requireContext(),
            { _, year, monthOfYear, dayOfMonth ->
                val selectedMonth = midnightCalendar
                selectedMonth.set(year, monthOfYear, dayOfMonth)
                month = selectedMonth
            },
            month[Calendar.YEAR],
            month[Calendar.MONTH],
            month[Calendar.DAY_OF_MONTH]
        ).show()
    }

    private fun onSelectMonth(month: Calendar) {
        mBinding.currentMonthLabel.text = sdfMonthAndYear.format(month.timeInMillis)
    }


    interface DayBindHelper {
        fun onBindDay(binding: DayCellLayoutBinding, date: Calendar)
    }

    companion object {
        var listener: DayBindHelper? = null
    }
}
