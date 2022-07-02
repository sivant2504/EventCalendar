package com.sivan.calendar.widget

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View.*
import android.view.ViewGroup
import android.widget.TableLayout
import androidx.recyclerview.widget.RecyclerView
import com.sivan.calendar.databinding.DayCellLayoutBinding
import com.sivan.calendar.databinding.TableMonthBinding
import com.sivan.calendar.databinding.TableRowWeekBinding
import com.sivan.calendar.widget.Attributes.Companion.currentMonthDayTextColor
import com.sivan.calendar.widget.Attributes.Companion.greenMsgTextColor
import com.sivan.calendar.widget.Attributes.Companion.otherMonthDayTextColor
import com.sivan.calendar.widget.Attributes.Companion.redMsgTextColor
import com.sivan.calendar.widget.Attributes.Companion.todayTextColor
import com.sivan.calendar.widget.CalendarWidget.Companion.bufferSize
import com.sivan.calendar.widget.CalendarWidget.Companion.listener
import com.sivan.calendar.widget.CalendarWidget.Companion.mCenterPosition
import java.util.*
import java.util.Calendar.MONTH

@SuppressLint("NotifyDataSetChanged")
class MonthAdapter(private val layoutInflater: LayoutInflater) :
    RecyclerView.Adapter<MonthAdapter.MonthHolder>() {
    var months = ArrayList<Calendar>()
    private var bindingMonth = midnightCalendar

    init {
        resetMonth()
    }

    fun resetMonth(month: Calendar = midnightCalendar) {
        month.setMidnight()
        months.clear()
        for (i in 0 until bufferSize) {
            months.add(month.copy().apply { add(MONTH, i - mCenterPosition) })
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MonthHolder {
        return MonthHolder(
            TableMonthBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MonthHolder, position: Int) {
        bindingMonth = months[position]
        populateMonth(holder.binding.tlMonth, bindingMonth)
    }

    override fun getItemCount() = months.size

    private fun populateMonth(tableLayout: TableLayout, month: Calendar) {
        val startOfMonth = month.copy()
        startOfMonth.set(Calendar.DAY_OF_MONTH, 1)
        val startOfWeek = midnightCalendar.apply { timeInMillis = startOfMonth.timeInMillis }
        startOfWeek.set(Calendar.DAY_OF_WEEK, startOfWeek.firstDayOfWeek)

        tableLayout.removeAllViews()
        for (i in 0 until 6) {
            val weekRow = TableRowWeekBinding.inflate(layoutInflater, tableLayout, true)
            weekRow.bind(startOfWeek)
            startOfWeek.apply { add(Calendar.DATE, 7) }
        }
    }

    private fun TableRowWeekBinding.bind(startDay: Calendar) {
        for (i in 0 until 7) {
            val dayCell = DayCellLayoutBinding.inflate(layoutInflater, this@bind.trWeek, true)
            dayCell.bind(startDay.copy().apply { add(Calendar.DATE, i) })
        }
    }

    private fun DayCellLayoutBinding.bind(day: Calendar) {
        dayNumberLabel.text = day[Calendar.DAY_OF_MONTH].toString()
        //dayNumberLabel.text = sdfDate.format(day.timeInMillis)
        if (this.initColorSettings(day)) {
            listener?.onBindDay(this, day)
        }
    }

    private fun DayCellLayoutBinding.initColorSettings(day: Calendar): Boolean {
        if (day.isNotInMonth(bindingMonth)) {
            dayNumberLabel.setColor(otherMonthDayTextColor)
            tvRedMsg.visibility = INVISIBLE
            tvGreenMsg.visibility = INVISIBLE
            return false
        } else if (day.isToday) {
            dayNumberLabel.setColor(todayTextColor)
        } else {
            dayNumberLabel.setColor(currentMonthDayTextColor)
        }
        tvGreenMsg.setColor(greenMsgTextColor)
        tvGreenMsg.visibility = VISIBLE
        tvRedMsg.setColor(redMsgTextColor)
        tvRedMsg.visibility = VISIBLE
        return true
    }

    inner class MonthHolder(val binding: TableMonthBinding) :
        RecyclerView.ViewHolder(binding.root)
/*
    fun logList() {
        months.forEach {
            it.log("Months Log")
        }
    }*/
}