package com.sivan.calendar.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.sivan.calendar.databinding.FragmentMonthBinding
import java.util.*
import java.util.Calendar.DATE
import kotlin.collections.ArrayList

class MonthFragment : Fragment() {
    private var _binding: FragmentMonthBinding? = null
    private val binding get() = _binding!!
    private val days = ArrayList<Calendar>(42)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMonthBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prepareDaysArray(month)
        binding.rvMonth.layoutManager = GridLayoutManager(requireContext(), 7)
        binding.rvMonth.adapter = MonthFragmentAdapter(days)
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun prepareDaysArray(month: Calendar){
        val startOfMonth = month.copy()
        startOfMonth.set(Calendar.DAY_OF_MONTH, 1)
        val startOfWeek = midnightCalendar.apply { timeInMillis = startOfMonth.timeInMillis }
        startOfWeek.set(Calendar.DAY_OF_WEEK, startOfWeek.firstDayOfWeek)

        days.clear()
        for (i in 0 until 42){
            val day = startOfWeek.copy()
            day.add(DATE, i)
            days.add(day)
            //days[i].log("$i")
        }
    }
    companion object {
        var month = midnightCalendar

        @JvmStatic
        fun newInstance(month: Calendar): MonthFragment {
            Companion.month = month.copy().setMidnight()
            return MonthFragment()
        }
    }
}