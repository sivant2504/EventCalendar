package com.sivan.calendarfragment.calendarfragment

import android.content.Context
import androidx.core.content.ContextCompat
import com.sivan.calendarfragment.R
import com.sivan.calendarfragment.calendarfragment.FragmentAttributes.Companion.currentMonthDayTextColor
import com.sivan.calendarfragment.calendarfragment.FragmentAttributes.Companion.greenMsgTextColor
import com.sivan.calendarfragment.calendarfragment.FragmentAttributes.Companion.otherMonthDayTextColor
import com.sivan.calendarfragment.calendarfragment.FragmentAttributes.Companion.redMsgTextColor
import com.sivan.calendarfragment.calendarfragment.FragmentAttributes.Companion.todayTextColor


class FragmentAttributes(context: Context) {

    init {
        primaryColor = context.getColorResCompat(androidx.appcompat.R.attr.colorPrimary)
        accentColor = context.getColorResCompat(androidx.appcompat.R.attr.colorAccent)

        initAttributes(context)
    }

    private fun initAttributes(context: Context) {
        todayTextColor = primaryColor
        currentMonthDayTextColor =
            ContextCompat.getColor(context, R.color.current_month_day_text_color)
        otherMonthDayTextColor = ContextCompat.getColor(context, R.color.other_month_day_text_color)
        greenMsgTextColor = ContextCompat.getColor(context, R.color.green_message_text_color)
        redMsgTextColor = ContextCompat.getColor(context, R.color.red_message_text_color)
    }

    companion object {
        var primaryColor = -1
        var accentColor = -1

        var currentMonthDayTextColor = -1
        var todayTextColor = -1
        var otherMonthDayTextColor = -1
        var greenMsgTextColor = -1
        var redMsgTextColor = -1
    }
}

fun CalendarFragment.setCurrentMothDayColor(res: Int): CalendarFragment {
    currentMonthDayTextColor = res
    return this
}

fun CalendarFragment.setOtherMothDayColor(res: Int): CalendarFragment {
    otherMonthDayTextColor = res
    return this
}

fun CalendarFragment.setTodayTextColor(res: Int): CalendarFragment {
    todayTextColor = res
    return this
}

fun CalendarFragment.setGreenMessageTextColor(res: Int): CalendarFragment {
    greenMsgTextColor = res
    return this
}

fun CalendarFragment.setRedMessageTextColor(res: Int): CalendarFragment {
    redMsgTextColor = res
    return this
}