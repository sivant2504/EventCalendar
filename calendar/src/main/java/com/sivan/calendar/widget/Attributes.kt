package com.sivan.calendar.widget

import android.content.Context
import androidx.core.content.ContextCompat
import com.sivan.calendar.R
import com.sivan.calendar.widget.Attributes.Companion.currentMonthDayTextColor
import com.sivan.calendar.widget.Attributes.Companion.greenMsgTextColor
import com.sivan.calendar.widget.Attributes.Companion.otherMonthDayTextColor
import com.sivan.calendar.widget.Attributes.Companion.redMsgTextColor
import com.sivan.calendar.widget.Attributes.Companion.todayTextColor

class Attributes(context: Context) {

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

fun CalendarWidget.setCurrentMothDayColor(res: Int): CalendarWidget {
    currentMonthDayTextColor = res
    return this
}

fun CalendarWidget.setOtherMothDayColor(res: Int): CalendarWidget {
    otherMonthDayTextColor = res
    return this
}

fun CalendarWidget.setTodayTextColor(res: Int): CalendarWidget {
    todayTextColor = res
    return this
}

fun CalendarWidget.setGreenMessageTextColor(res: Int): CalendarWidget {
    greenMsgTextColor = res
    return this
}

fun CalendarWidget.setRedMessageTextColor(res: Int): CalendarWidget {
    redMsgTextColor = res
    return this
}