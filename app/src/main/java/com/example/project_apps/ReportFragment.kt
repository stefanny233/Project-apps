package com.example.project_apps

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.project_apps.databinding.FragmentReportBinding
import com.google.android.material.tabs.TabLayoutMediator

class ReportFragment : Fragment() {

    private var _binding: FragmentReportBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentReportBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = ReportPagerAdapter(this)
        binding.viewPagerReport.adapter = adapter

        TabLayoutMediator(binding.tabLayoutReport, binding.viewPagerReport) { tab, position ->
            tab.text = when (position) {
                0 -> "Form Pengaduan"
                1 -> "History Pengaduan"
                else -> throw IllegalArgumentException("Posisi halaman tidak valid")
            }
        }.attach()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}