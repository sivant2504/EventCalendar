package com.sivan.calendar.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Typeface
import android.util.Log
import android.util.TypedValue
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import java.text.SimpleDateFormat
import java.util.*
import java.util.Calendar.MONTH

internal fun Context.toast(message: CharSequence) =
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

val sdfDate = SimpleDateFormat("EEE/dd/MMM", Locale.ENGLISH)

val sdfMonthAndYear = SimpleDateFormat("MMMM yyyy", Locale.ENGLISH)

val sdfShortWeekDay = SimpleDateFormat("EEE", Locale.ENGLISH)

fun Context.primaryColor(): Int {
    val typedValue = TypedValue()
    theme.resolveAttribute(androidx.appcompat.R.attr.colorPrimary, typedValue, true)
    return typedValue.data
}

@ColorInt
@SuppressLint("ResourceAsColor")
fun Context.getColorResCompat(@AttrRes id: Int): Int {
    val resolvedAttr = TypedValue()
    theme.resolveAttribute(id, resolvedAttr, true)
    val colorRes = resolvedAttr.run { if (resourceId != 0) resourceId else data }
    return ContextCompat.getColor(this, colorRes)
}

val midnightCalendar: Calendar
    get() = Calendar.getInstance().apply {
        this.setMidnight()
    }

fun Calendar.setMidnight() = this.apply {
    set(Calendar.HOUR_OF_DAY, 0)
    set(Calendar.MINUTE, 0)
    set(Calendar.SECOND, 0)
    set(Calendar.MILLISECOND, 0)
}

fun Calendar.copy(): Calendar {
    return this.clone() as Calendar
}

val Calendar.isToday
    get() = this == midnightCalendar

fun Calendar.isNotInMonth(month: Calendar): Boolean {
    return this.get(MONTH) != month.get(MONTH)
}

fun TextView.setColor(
    textColor: Int,
    typeface: Typeface? = null
) {
    typeface?.let { setTypeface(typeface) }
    setTextColor(textColor)
}

fun Calendar.log(label: String = "Date") {
    Log.e(label, sdfDate.format(timeInMillis))
}
