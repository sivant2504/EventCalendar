package com.sivan.calendarfragment.calendarfragment

import android.view.LayoutInflater
import android.view.View.VISIBLE
import android.view.View.INVISIBLE
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sivan.calendarfragment.calendarfragment.CalendarFragment.Companion.listener
import com.sivan.calendarfragment.calendarfragment.MonthFragment.Companion.month
import com.sivan.calendarfragment.databinding.DayCellLayoutBinding
import java.util.*
import kotlin.collections.ArrayList

class MonthFragmentAdapter(private var days: ArrayList<Calendar>) :
    RecyclerView.Adapter<MonthFragmentAdapter.GridViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GridViewHolder {
        return GridViewHolder(
            DayCellLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: GridViewHolder, position: Int) {
        holder.binding.bind(days[position])
    }

    override fun getItemCount() = days.size

    private fun DayCellLayoutBinding.bind(day: Calendar) {
        dayNumberLabel.text = day[Calendar.DAY_OF_MONTH].toString()
        if (this.initColorSettings(day)) {
            listener?.onBindDay(this, day)
        }
    }

    private fun DayCellLayoutBinding.initColorSettings(day: Calendar): Boolean {
        if (day.isNotInMonth(month)) {
            dayNumberLabel.setColor(FragmentAttributes.otherMonthDayTextColor)
            tvRedMsg.visibility = INVISIBLE
            tvGreenMsg.visibility = INVISIBLE
            return false
        } else if (day.isToday) {
            dayNumberLabel.setColor(FragmentAttributes.todayTextColor)
        } else {
            dayNumberLabel.setColor(FragmentAttributes.currentMonthDayTextColor)
        }
        tvGreenMsg.setColor(FragmentAttributes.greenMsgTextColor)
        tvGreenMsg.visibility = VISIBLE
        tvRedMsg.setColor(FragmentAttributes.redMsgTextColor)
        tvRedMsg.visibility = VISIBLE
        return true
    }

    inner class GridViewHolder(val binding: DayCellLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)
}