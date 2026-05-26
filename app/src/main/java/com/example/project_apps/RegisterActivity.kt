package com.example.project_apps

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.project_apps.databinding.ActivityRegisterBinding
import java.util.Calendar

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Spinner Agama
        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.agama_list,
            android.R.layout.simple_spinner_item
        )

        adapter.setDropDownViewResource(
            android.R.layout.simple_spinner_dropdown_item
        )

        binding.spAgama.adapter = adapter

        // Date Picker
        binding.etTglLahir.setOnClickListener {

            val c = Calendar.getInstance()

            DatePickerDialog(
                this,
                { _, year, month, day ->

                    binding.etTglLahir.setText(
                        "$day/${month + 1}/$year"
                    )

                },
                c.get(Calendar.YEAR),
                c.get(Calendar.MONTH),
                c.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        // Button Submit
        binding.btnSubmit.setOnClickListener {
            validate()
        }
    }

    private fun validate() {

        val nama = binding.etNama.text.toString()
        val tgl = binding.etTglLahir.text.toString()
        val user = binding.etUsernameReg.text.toString()
        val pass = binding.etPassReg.text.toString()
        val conf = binding.etConfirmPass.text.toString()
        val agama = binding.spAgama.selectedItem.toString()
        val genderId = binding.rgGender.checkedRadioButtonId

        if (
            nama.isEmpty() ||
            tgl.isEmpty() ||
            user.isEmpty() ||
            pass.isEmpty() ||
            conf.isEmpty() ||
            genderId == -1
        ) {

            Toast.makeText(
                this,
                "Semua data wajib diisi!",
                Toast.LENGTH_SHORT
            ).show()

            return
        }

        if (pass != conf) {

            binding.etConfirmPass.error =
                "Password tidak cocok!"

            return
        }

        val genderText =
            findViewById<RadioButton>(genderId).text.toString()

        // Simpan SharedPreferences
        val sp = getSharedPreferences(
            "UserDB",
            Context.MODE_PRIVATE
        )

        val editor = sp.edit()

        editor.putString("saved_nama", nama)
        editor.putString("saved_tgl", tgl)
        editor.putString("saved_gender", genderText)
        editor.putString("saved_agama", agama)
        editor.putString("saved_user", user)
        editor.putString("saved_pass", pass)

        editor.apply()

        Toast.makeText(
            this,
            "Registrasi Berhasil!",
            Toast.LENGTH_SHORT
        ).show()

        finish()
    }
}