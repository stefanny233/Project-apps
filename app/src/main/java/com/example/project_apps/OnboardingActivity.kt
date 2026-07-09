package com.example.project_apps

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.project_apps.databinding.ActivityOnboardingBinding // Mengaktifkan ViewBinding mase

class OnboardingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOnboardingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        binding = ActivityOnboardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

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

        binding.dotsIndicator.setViewPager2(binding.viewPagerOnboarding)

        binding.viewPagerOnboarding.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
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
                binding.viewPagerOnboarding.currentItem = currentPos + 1
            } else {
                val intent = Intent(this, AuthActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }
}