package com.example.project_apps

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
// Hapus import android.R jika ada!
import com.example.project_apps.databinding.ActivityRegisterBinding
import java.util.Calendar

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 1. Setup Spinner Agama (Pakai android.R.layout bawaan sistem)
        val listAgama = arrayOf("Pilih Agama", "Islam", "Kristen", "Katolik", "Hindu", "Budha", "Konghucu")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, listAgama)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spAgama.adapter = adapter

        // 2. Setup DatePicker
        binding.etTglLahir.setOnClickListener {
            val c = Calendar.getInstance()
            val datePickerDialog = DatePickerDialog(this, { _, year, month, day ->
                binding.etTglLahir.setText("$day/${month + 1}/$year")
            }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH))
            datePickerDialog.show()
        }

        binding.btnSubmit.setOnClickListener {
            validateAndSave()
        }
    }

    private fun validateAndSave() {
        // Ambil data sesuai ID di XML kamu
        val nama = binding.etNama.text.toString()
        val tgl = binding.etTglLahir.text.toString()
        val user = binding.etUsernameReg.text.toString()
        val pass = binding.etPassReg.text.toString()
        val confPass = binding.etConfirmPass.text.toString()
        val agama = binding.spAgama.selectedItem?.toString() ?: ""
        val genderId = binding.rgGender.checkedRadioButtonId

        // Validasi Kosong
        if (nama.isEmpty() || tgl.isEmpty() || user.isEmpty() || pass.isEmpty() || genderId == -1 || agama == "Pilih Agama") {
            Toast.makeText(this, "Woi! Semua data wajib diisi!", Toast.LENGTH_SHORT).show()
            return
        }

        // Validasi Password Sama
        if (pass != confPass) {
            binding.etConfirmPass.error = "Password gak sama jer!"
            return
        }

        // Simpan ke SharedPreference
        val sharedPref = getSharedPreferences("UserDB", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putString("saved_user", user)
        editor.putString("saved_pass", pass)
        editor.putString("saved_nama", nama)
        editor.apply()

        Toast.makeText(this, "Registrasi Berhasil!", Toast.LENGTH_SHORT).show()

        // BALIK KE LOGIN
        finish()
    }
}