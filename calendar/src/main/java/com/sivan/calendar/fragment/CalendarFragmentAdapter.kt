package com.sivan.calendar.fragment

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import java.util.*
import kotlin.collections.ArrayList

class CalendarFragmentAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle, private val months: ArrayList<Calendar>) :
    FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int {
        return months.size
    }

    override fun createFragment(position: Int): Fragment {
        return MonthFragment.newInstance(months[position])
    }
}