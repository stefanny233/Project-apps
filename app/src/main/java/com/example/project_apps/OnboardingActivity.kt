package com.example.project_apps

import android.content.Context // Import Context untuk akses SharedPreferences
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.project_apps.databinding.ActivityOnboardingBinding

class OnboardingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOnboardingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        binding = ActivityOnboardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Data konten lembar halaman onboarding aplikasi Bina Desa mase
        val onboardingPages = listOf(
            OnboardingPage(
                "Digitalisasi Desa",
                "Membantu pemetaan dan pendataan potensi desa secara digital dan akurat.",
                R.drawable.img_onboarding_1
            ),
            OnboardingPage(
                "Edukasi Teknologi",
                "Memberikan pelatihan IT untuk masyarakat guna meningkatkan SDM desa.",
                R.drawable.img_onboarding_2
            ),
            OnboardingPage(
                "Harmoni Sosial",
                "Mempererat silaturahmi antar warga melalui platform informasi terintegrasi.",
                R.drawable.img_onboarding_3
            )
        )

        val adapter = OnboardingAdapter(onboardingPages)
        binding.viewPagerOnboarding.adapter = adapter

        // Hubungkan titik-titik indikator halaman mase
        binding.dotsIndicator.setViewPager2(binding.viewPagerOnboarding)

        // Deteksi pergerakan swipe halaman mase
        binding.viewPagerOnboarding.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                // Jika berada di halaman terakhir, ubah text tombol jadi "Ayo Mulai"
                if (position == onboardingPages.size - 1) {
                    binding.btnActionOnboarding.text = "Ayo Mulai"
                } else {
                    binding.btnActionOnboarding.text = "Lanjut"
                }
            }
        })

        binding.btnActionOnboarding.setOnClickListener {
            val currentPos = binding.viewPagerOnboarding.currentItem
            if (currentPos < onboardingPages.size - 1) {
                // Geser ke halaman berikutnya mase
                binding.viewPagerOnboarding.currentItem = currentPos + 1
            } else {
                val sharedPref = getSharedPreferences("user_pref", Context.MODE_PRIVATE)
                sharedPref.edit().putBoolean("isFirstRun", false).apply()

                val intent = Intent(this, AuthActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }
}