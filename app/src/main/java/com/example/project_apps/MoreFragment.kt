package com.example.project_apps

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.project_apps.databinding.FragmentMoreBinding
import com.google.android.material.tabs.TabLayout

class MoreFragment : Fragment() {
    private var _binding: FragmentMoreBinding? = null
    private val binding get() = _binding!!

    // Siapkan list data dummy
    private val listDesa = ArrayList<BinaDesaModel>()
    private val listProgram = ArrayList<BinaDesaModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMoreBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnToSetting.setOnClickListener {
            val intent = Intent(requireContext(), SettingActivity::class.java)
            startActivity(intent)
        }

        binding.btnToHome.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, HomeFragment())
                .commit()
        }

        setupDummyData()

        binding.rvBinaDesa.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.rvBinaDesa.adapter = BinaDesaAdapter(listDesa)

        binding.tabLayoutMore.removeAllTabs()

        val tab1 = binding.tabLayoutMore.newTab().setText("Daftar Desa")
        tab1.icon = ContextCompat.getDrawable(requireContext(), android.R.drawable.ic_menu_myplaces)
        binding.tabLayoutMore.addTab(tab1)

        val tab2 = binding.tabLayoutMore.newTab().setText("Program Kerja")
        tab2.icon = ContextCompat.getDrawable(requireContext(), android.R.drawable.ic_menu_edit)
        binding.tabLayoutMore.addTab(tab2)

        binding.tabLayoutMore.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    0 -> binding.rvBinaDesa.adapter = BinaDesaAdapter(listDesa)
                    1 -> binding.rvBinaDesa.adapter = BinaDesaAdapter(listProgram)
                }
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })

        // Notifikasi badge merah penyejuk hati
        binding.tabLayoutMore.post {
            binding.tabLayoutMore.getTabAt(0)?.getOrCreateBadge()?.isVisible = true
            binding.tabLayoutMore.getTabAt(1)?.getOrCreateBadge()?.apply {
                isVisible = true
                number = 3
            }
        }
    }

    private fun setupDummyData() {
        listDesa.clear()
        // 10 Data Dummy untuk Daftar Desa Binaaan
        listDesa.add(BinaDesaModel("Desa Sukamaju", "Kecamatan Rumbai, Pekanbaru", "https://picsum.photos/seed/desa1/400/300"))
        listDesa.add(BinaDesaModel("Desa Makmur Jaya", "Kabupaten Kampar, Riau", "https://picsum.photos/seed/desa2/400/300"))
        listDesa.add(BinaDesaModel("Desa Suka Karya", "Kecamatan Tampan, Pekanbaru", "https://picsum.photos/seed/desa3/400/300"))
        listDesa.add(BinaDesaModel("Desa Rimba Panjang", "Kecamatan Tambang, Kampar", "https://picsum.photos/seed/desa4/400/300"))
        listDesa.add(BinaDesaModel("Desa Muara Takus", "Kecamatan XIII Koto Kampar", "https://picsum.photos/seed/desa5/400/300"))
        listDesa.add(BinaDesaModel("Desa Sri Meranti", "Kecamatan Rumbai Pesisir", "https://picsum.photos/seed/desa6/400/300"))
        listDesa.add(BinaDesaModel("Desa Tanah Merah", "Kecamatan Siak Hulu, Kampar", "https://picsum.photos/seed/desa7/400/300"))
        listDesa.add(BinaDesaModel("Desa Tarai Bangun", "Kecamatan Tambang, Kampar", "https://picsum.photos/seed/desa8/400/300"))
        listDesa.add(BinaDesaModel("Desa Pandau Jaya", "Kecamatan Siak Hulu, Kampar", "https://picsum.photos/seed/desa9/400/300"))
        listDesa.add(BinaDesaModel("Desa Karya Indah", "Kecamatan Tapung, Kampar", "https://picsum.photos/seed/desa10/400/300"))

        listProgram.clear()
        // 10 Data Dummy untuk Program Kerja Digitalisasi Desa
        listProgram.add(BinaDesaModel("Edukasi Digital Desa", "Pelatihan pembuatan web profile desa", "https://picsum.photos/seed/program1/400/300"))
        listProgram.add(BinaDesaModel("Bina Desa Sehat", "Pemeriksaan kesehatan gratis bagi lansia", "https://picsum.photos/seed/program2/400/300"))
        listProgram.add(BinaDesaModel("Sosialisasi Pertanian", "Penyuluhan pupuk organik modern", "https://picsum.photos/seed/program3/400/300"))
        listProgram.add(BinaDesaModel("Internet Sehat", "Edukasi penggunaan internet untuk anak-anak", "https://picsum.photos/seed/program4/400/300"))
        listProgram.add(BinaDesaModel("UMKM Desa Go Digital", "Pelatihan pemasaran produk via e-commerce", "https://picsum.photos/seed/program5/400/300"))
        listProgram.add(BinaDesaModel("Desa Siaga Bencana", "Simulasi penanggulangan kebakaran hutan", "https://picsum.photos/seed/program6/400/300"))
        listProgram.add(BinaDesaModel("Taman Baca Digital", "Pengadaan e-book gratis untuk perpustakaan", "https://picsum.photos/seed/program7/400/300"))
        listProgram.add(BinaDesaModel("Pelatihan Operator Desa", "Optimalisasi sistem administrasi digital", "https://picsum.photos/seed/program8/400/300"))
        listProgram.add(BinaDesaModel("Energi Surya Mandiri", "Pemasangan panel surya untuk lampu jalan", "https://picsum.photos/seed/program9/400/300"))
        listProgram.add(BinaDesaModel("Pemuda Sadar Hukum", "Sosialisasi pencegahan kejahatan siber", "https://picsum.photos/seed/program10/400/300"))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}