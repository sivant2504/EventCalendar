package com.sivan.calendarview

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.sivan.calendar.databinding.DayCellLayoutBinding
import com.sivan.calendar.fragment.CalendarFragment
import com.sivan.calendar.widget.CalendarWidget
import com.sivan.calendarview.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        CalendarFragment.listener = object: CalendarFragment.DayBindHelper{

            override fun onBindDay(
                binding: DayCellLayoutBinding,
                date: Calendar
            ) {
                binding.root.setOnClickListener {
                    Log.e("Day", "${date.time}")
                }
            }
        }

        CalendarWidget.listener = object: CalendarWidget.DayBindHelper{
            override fun onBindDay(binding: DayCellLayoutBinding, date: Calendar) {
                binding.root.setOnClickListener {
                    Log.e("Day", "${date.time}")
                }
            }
        }
    }
}