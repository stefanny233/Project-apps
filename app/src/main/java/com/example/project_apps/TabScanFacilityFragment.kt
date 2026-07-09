package com.example.project_apps

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableRow
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.project_apps.databinding.FragmentTabScanFacilityBinding

class TabScanFacilityFragment : Fragment() {

    private var _binding: FragmentTabScanFacilityBinding? = null
    private val binding get() = _binding!!

    private val sharedViewModel: SharedReportViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentTabScanFacilityBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedViewModel.laporanBaru.observe(viewLifecycleOwner) { dataLaporan ->
            tambahKeTabelHistory(dataLaporan.first, dataLaporan.second)
        }
    }

    private fun tambahKeTabelHistory(namaPelapor: String, isiKeluhan: String) {
        val contextNonNull = context ?: return

        val cardView = com.google.android.material.card.MaterialCardView(contextNonNull).apply {
            layoutParams = android.widget.LinearLayout.LayoutParams(
                android.widget.LinearLayout.LayoutParams.MATCH_PARENT,
                android.widget.LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                setMargins(0, 0, 0, 32) // Jarak antar kartu biar gak dempet
            }
            radius = 24f
            cardElevation = 6f
            setCardBackgroundColor(Color.WHITE)
            strokeWidth = 0
        }

        val cardContainer = android.widget.LinearLayout(contextNonNull).apply {
            orientation = android.widget.LinearLayout.VERTICAL
            setPadding(40, 40, 40, 40)
        }

        val headerLayout = android.widget.LinearLayout(contextNonNull).apply {
            orientation = android.widget.LinearLayout.HORIZONTAL
            gravity = android.view.Gravity.CENTER_VERTICAL
        }

        val tvIcon = TextView(contextNonNull).apply {
            text = "👤"
            textSize = 20f
            setPadding(0, 0, 16, 0)
        }

        val txtPelapor = TextView(contextNonNull).apply {
            text = namaPelapor
            setTextColor(Color.parseColor("#1B5E20")) // Warna hijau tua premium
            textSize = 16f
            typeface = Typeface.DEFAULT_BOLD
        }

        headerLayout.addView(tvIcon)
        headerLayout.addView(txtPelapor)

        val divider = View(contextNonNull).apply {
            layoutParams = android.widget.LinearLayout.LayoutParams(
                android.widget.LinearLayout.LayoutParams.MATCH_PARENT,
                2
            ).apply {
                setMargins(0, 24, 0, 24)
            }
            setBackgroundColor(Color.parseColor("#E8F5E9"))
        }

        val txtKeluhan = TextView(contextNonNull).apply {
            text = isiKeluhan
            setTextColor(Color.parseColor("#424242"))
            textSize = 15f
            setLineSpacing(6f, 1f)
        }

        cardContainer.addView(headerLayout)
        cardContainer.addView(divider)
        cardContainer.addView(txtKeluhan)

        cardView.addView(cardContainer)

        binding.tableLayoutHistory.addView(cardView, 0)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}