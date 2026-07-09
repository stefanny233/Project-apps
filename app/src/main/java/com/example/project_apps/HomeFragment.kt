package com.example.project_apps

import android.Manifest
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.project_apps.data.api.PhotoApiClient
import com.example.project_apps.databinding.FragmentHomeBinding
import com.example.project_apps.home.photo.PhotoAdapter
import com.example.project_apps.utils.PermissionHelper
import com.example.project_apps.utils.ReminderHelper
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val notificationPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                Toast.makeText(requireContext(), "Notifikasi diizinkan mase!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Notifikasi ditolak, fitur pengingat tidak optimal", Toast.LENGTH_SHORT).show()
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadApiPhotos()

        checkNotificationPermission()

        binding.btnWebView.setOnClickListener {
            val intent = Intent(requireContext(), WebViewActivity::class.java)

            // 🚀 URL sudah dikunci ke halaman login web bina desa yang valid mase
            intent.putExtra("URL_WEB", "https://iyanyow.alwaysdata.net/login")
            startActivity(intent)

            ReminderHelper.setReminder(
                context = requireContext(),
                minutesFromNow = 1,
                title = "Pengingat Kegiatan Desa",
                message = "Halo User, jangan lupa waktu rapat koordinasi Bina Desa dimulai sekarang!"
            )
            Toast.makeText(requireContext(), "Layanan dibuka & Pengingat diset 1 menit lagi...", Toast.LENGTH_LONG).show()
        }

        binding.btnLogout.setOnClickListener {
            AlertDialog.Builder(requireContext())
                .setTitle("Keluar Aplikasi")
                .setMessage("Apakah kamu yakin ingin mengakhiri sesi ini?")
                .setPositiveButton("Ya, Keluar") { _, _ ->
                    val sharedPref = requireActivity().getSharedPreferences("user_pref", Context.MODE_PRIVATE)

                    // 🚀 AMAN: Set isLogin jadi false saja, jangan pakai .clear() biar data register kamu tidak hilang mase!
                    sharedPref.edit().putBoolean("isLogin", false).apply()

                    val intent = Intent(requireContext(), AuthActivity::class.java)
                    startActivity(intent)

                    requireActivity().finish()
                }
                .setNegativeButton("Batal", null)
                .show()
        }
    }

    private fun checkNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val permission = Manifest.permission.POST_NOTIFICATIONS
            if (!PermissionHelper.hasPermission(requireContext(), permission)) {
                PermissionHelper.requestPermission(notificationPermissionLauncher, permission)
            }
        }
    }

    private fun loadApiPhotos() {
        viewLifecycleOwner.lifecycleScope.launch {
            try {
                val photoList = PhotoApiClient.apiService.getPhotos()
                if (photoList.isNotEmpty()) {
                    val photoAdapter = PhotoAdapter(photoList)
                    binding.rvGallery.adapter = photoAdapter
                    binding.rvGallery.layoutManager = GridLayoutManager(requireContext(), 2)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(requireContext(), "Gagal memuat galeri desa terpadu", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}