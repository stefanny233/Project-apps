package com.example.project_apps

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatActivity
import com.example.project_apps.databinding.ActivityRegisterBinding
import java.util.Calendar

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = ArrayAdapter.createFromResource(this, R.array.agama_list, android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spAgama.adapter = adapter

        binding.etTglLahir.setOnClickListener {
            val c = Calendar.getInstance()
            DatePickerDialog(this, { _, year, month, day ->
                binding.etTglLahir.setText("$day/${month + 1}/$year")
                binding.tilTglLahir.error = null
            }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show()
        }

        binding.btnSubmit.setOnClickListener { validate() }
    }

    private fun validate() {
        val nama = binding.etNama.text.toString()
        val tgl = binding.etTglLahir.text.toString()
        val user = binding.etUsernameReg.text.toString()
        val pass = binding.etPassReg.text.toString()
        val conf = binding.etConfirmPass.text.toString()
        val agama = binding.spAgama.selectedItem.toString()
        val genderId = binding.rgGender.checkedRadioButtonId

        var isValid = true

        binding.tilNama.error = null
        binding.tilTglLahir.error = null
        binding.tilUser.error = null
        binding.tilPass.error = null
        binding.tilConfirm.error = null
        binding.tvErrorGender.visibility = View.GONE

        if (nama.isEmpty()) { binding.tilNama.error = "Nama wajib diisi!"; isValid = false }
        if (tgl.isEmpty()) { binding.tilTglLahir.error = "Pilih tanggal lahir!"; isValid = false }
        if (user.isEmpty()) { binding.tilUser.error = "Username kosong!"; isValid = false }
        if (pass.isEmpty()) { binding.tilPass.error = "Password kosong!"; isValid = false }
        if (genderId == -1) { binding.tvErrorGender.visibility = View.VISIBLE; isValid = false }

        if (pass != conf) {
            binding.tilConfirm.error = "Password tidak cocok!"
            isValid = false
        }

        if (isValid) {
            // --- BAGIAN YANG DISESUAIKAN (SIMPAN LENGKAP) ---
            val sp = getSharedPreferences("UserDB", Context.MODE_PRIVATE)
            val edit = sp.edit()
            val genderText = findViewById<RadioButton>(genderId).text.toString()

            edit.putString("sp_user", user)
            edit.putString("sp_pass", pass)
            edit.putString("sp_nama", nama)
            edit.putString("sp_tgl", tgl)
            edit.putString("sp_gender", genderText)
            edit.putString("sp_agama", agama)
            edit.apply()

            finish()
        }
    }
}