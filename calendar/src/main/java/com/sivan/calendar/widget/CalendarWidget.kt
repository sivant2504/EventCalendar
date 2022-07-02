package com.sivan.calendar.widget

import android.app.DatePickerDialog
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.viewpager2.widget.ViewPager2
import com.sivan.calendar.R
import com.sivan.calendar.databinding.CalendarWidgetBinding
import com.sivan.calendar.databinding.DayCellLayoutBinding
import java.util.*
import java.util.Calendar.MONTH

class CalendarWidget @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {
    private var mainBinding: CalendarWidgetBinding
    private lateinit var mAdapter: MonthAdapter
    private var monthCopy = midnightCalendar
    var month: Calendar
        get() {
            return monthCopy
        }
        set(month) {
            mainBinding.calendarViewPager.post {
                mAdapter.resetMonth(month)
                onSelectMonth(month)
                mainBinding.calendarViewPager.setCurrentItem(mCenterPosition, true)
            }
        }

    init {
        LayoutInflater.from(context).inflate(R.layout.calendar_widget, this)
        mainBinding = CalendarWidgetBinding.bind(this)

        mainBinding.calendarViewPager.post {
            mAdapter = MonthAdapter(LayoutInflater.from(context))
            mainBinding.calendarViewPager.apply {
                adapter = mAdapter
                post {
                    offscreenPageLimit = 3
                    registerOnPageChangeCallback(object :
                        ViewPager2.OnPageChangeCallback() {
                        override fun onPageScrollStateChanged(state: Int) {
                            super.onPageScrollStateChanged(state)
                            if (state == ViewPager2.SCROLL_STATE_IDLE) {
                                onSelectMonth(mainBinding.calendarViewPager.currentItem)
                            }
                        }

                        override fun onPageSelected(position: Int) {
                            super.onPageSelected(position)
                            onSelectMonth(mAdapter.months[position])
                        }
                    })
                    setCurrentItem(mCenterPosition, false)
                    initHeader()
                    Attributes(context)
                }
            }
        }
    }

    private fun initHeader() {
        mainBinding.currentMonthLabel.setOnClickListener { showDatePicker() }
        mainBinding.previousButton.setOnClickListener {
            mainBinding.calendarViewPager.apply { post { setCurrentItem(currentItem - 1, true) } }
        }
        mainBinding.forwardButton.setOnClickListener {
            mainBinding.calendarViewPager.apply { post { setCurrentItem(currentItem + 1, true) } }
        }
    }

    private fun showDatePicker() {
        DatePickerDialog(
            context,
            { _, year, monthOfYear, dayOfMonth ->
                val selectedMonth = midnightCalendar
                selectedMonth.set(year, monthOfYear, dayOfMonth)
                month = selectedMonth
            },
            month[Calendar.YEAR],
            month[MONTH],
            month[Calendar.DAY_OF_MONTH]
        ).show()
    }

    private fun onSelectMonth(index: Int) {
        when {
            index < mCenterPosition -> {
                val previousMonth = mAdapter.months[0].copy()
                previousMonth.add(MONTH, -1)
                mAdapter.months.add(0, previousMonth)
                mAdapter.notifyItemInserted(0)
                mAdapter.months.removeAt(bufferSize)
                mAdapter.notifyItemRemoved(bufferSize)
            }
            index > mCenterPosition -> {
                val nextMonth = mAdapter.months[bufferSize - 1].copy()
                nextMonth.add(MONTH, 1)
                mAdapter.months.add(bufferSize, nextMonth)
                mAdapter.notifyItemInserted(bufferSize)
                mAdapter.months.removeAt(0)
                mAdapter.notifyItemRemoved(0)
            }
        }
    }

    private fun onSelectMonth(month: Calendar) {
        monthCopy = month.copy()
        mainBinding.currentMonthLabel.text = sdfMonthAndYear.format(month.timeInMillis)
    }

    interface DayBindHelper {
        fun onBindDay(binding: DayCellLayoutBinding, date: Calendar)
    }

    companion object {
        var listener: DayBindHelper? = null
        var bufferSize = 7
        val mCenterPosition = (bufferSize - 1) / 2
    }
}
