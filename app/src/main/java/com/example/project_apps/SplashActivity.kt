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

        val sharedPref = getSharedPreferences("user_pref", Context.MODE_PRIVATE)
        val isLogin = sharedPref.getBoolean("isLogin", false)
        val isFirstRun = sharedPref.getBoolean("isFirstRun", true)

        Handler(Looper.getMainLooper()).postDelayed({
            if (isLogin) {
                startActivity(Intent(this, MainActivity::class.java))
            } else {
                if (isFirstRun) {
                    startActivity(Intent(this, OnboardingActivity::class.java))
                } else {
                    startActivity(Intent(this, AuthActivity::class.java))
                }
            }
            finish() // Tutup splash screen dari memory stack mase
        }, 3000)
    }
}