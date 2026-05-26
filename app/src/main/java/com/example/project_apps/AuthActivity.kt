package com.example.project_apps

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.project_apps.databinding.ActivityAuthBinding

class AuthActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAuthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {
            val username = binding.etUsername.text.toString()
            val password = binding.etPassword.text.toString()

            // Soal 3a: Ambil data dari SharedPreference UserDB
            val sharedPref = getSharedPreferences("UserDB", Context.MODE_PRIVATE)
            val savedUser = sharedPref.getString("saved_user", null)
            val savedPass = sharedPref.getString("saved_pass", null)

            // Soal 3b: Logika pengecekan login
            if (username.isNotEmpty() && password.isNotEmpty()) {
                if (username == savedUser && password == savedPass) {
                    // Login Berhasil
                    val editor = sharedPref.edit()
                    editor.putBoolean("isLogin", true)
                    editor.apply()

                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                } else {
                    // Soal 3b: Pesan error jika tidak sesuai
                    AlertDialog.Builder(this)
                        .setTitle("Login Gagal")
                        .setMessage("Username atau Password tidak sesuai dengan data registrasi!")
                        .setPositiveButton("OK", null)
                        .show()
                }
            } else {
                Toast.makeText(this, "Isi username dan password dahulu!", Toast.LENGTH_SHORT).show()
            }
        }

        binding.tvToRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
}