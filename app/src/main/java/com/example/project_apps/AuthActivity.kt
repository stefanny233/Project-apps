package com.example.project_apps

import android.content.Context
import android.content.Intent
import android.os.Bundle
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
            val inputUser = binding.etUsername.text.toString()
            val inputPass = binding.etPassword.text.toString()

            val sp = getSharedPreferences("UserDB", Context.MODE_PRIVATE)
            val savedUser = sp.getString("sp_user", null)
            val savedPass = sp.getString("sp_pass", null)

            val isRulePraktikum = (inputUser.isNotEmpty() && inputUser == inputPass)
            val isRuleRegistrasi = (inputUser == savedUser && inputPass == savedPass && savedUser != null)

            if (isRulePraktikum || isRuleRegistrasi) {
                // Login Berhasil
                val editor = sp.edit()
                editor.putBoolean("isLogin", true)
                editor.putString("username", inputUser)
                editor.apply()

                startActivity(Intent(this, MainActivity::class.java))
                finish()
            } else {
                // Login Gagal (Soal a2: Tampilkan isian yang tidak sesuai)
                binding.etUsername.error = "Username atau Password salah!"

                AlertDialog.Builder(this)
                    .setTitle("Login Gagal")
                    .setMessage("Username atau Password tidak sesuai dengan data registrasi!")
                    .setPositiveButton("OK", null)
                    .show()
            }
        }

        // Tambahkan fungsi pindah ke Register
        binding.tvToRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
}