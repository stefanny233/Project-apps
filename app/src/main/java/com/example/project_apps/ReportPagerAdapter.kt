package com.example.project_apps

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class ReportPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> TabForumReportFragment()
            1 -> TabScanFacilityFragment()
            else -> throw IllegalArgumentException("Posisi halaman tidak valid")
        }
    }
}