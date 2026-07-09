package com.example.project_apps

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.example.project_apps.databinding.ActivitySettingBinding
import com.google.android.material.snackbar.Snackbar

class SettingActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarSetting)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.toolbarSetting.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        val listMenu = listOf(
            "Kebijakan Privasi (Privacy Policy)",
            "Pusat Bantuan & Kontak",
            "Syarat & Ketentuan",
            "Keluar Akun"
        )

        val adapterBawaan = ArrayAdapter(this, android.R.layout.simple_list_item_1, listMenu)
        binding.listViewSetting.adapter = adapterBawaan

        binding.listViewSetting.setOnItemClickListener { _, parentView, position, _ ->
            val menuTerpilih = listMenu[position]
            Snackbar.make(parentView, "Membuka: $menuTerpilih", Snackbar.LENGTH_SHORT).show()
        }
    }
}