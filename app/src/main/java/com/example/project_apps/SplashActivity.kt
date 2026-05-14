package com.example.project_apps

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // Cek SharedPreferences sesuai modul "user_pref"
        val sharedPref = getSharedPreferences("user_pref", Context.MODE_PRIVATE)
        val isLogin = sharedPref.getBoolean("isLogin", false)

        Handler(Looper.getMainLooper()).postDelayed({
            if (isLogin) {
                // Jika sudah login, langsung ke Main
                startActivity(Intent(this, MainActivity::class.java))
            } else {
                // Jika belum, ke Auth (Login)
                startActivity(Intent(this, AuthActivity::class.java))
            }
            finish()
        }, 3000)
    }
}