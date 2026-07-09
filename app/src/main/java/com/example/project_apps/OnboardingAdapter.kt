package com.example.project_apps

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.project_apps.databinding.ItemOnboardingPageBinding // Import ViewBinding untuk layout item

class OnboardingAdapter(private val pages: List<OnboardingPage>) :
    RecyclerView.Adapter<OnboardingAdapter.OnboardingViewHolder>() {

    // Perbaikan kurung siku dan parameter binding.root di sini mase Stefan
    inner class OnboardingViewHolder(val binding: ItemOnboardingPageBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnboardingViewHolder {
        val binding = ItemOnboardingPageBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return OnboardingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OnboardingViewHolder, position: Int) {
        val page = pages[position]

        // Kita panggil langsung lewat holder.binding tanpa fungsi 'with' biar ga rawan linglung
        holder.binding.tvTitleOnboarding.text = page.title
        holder.binding.tvDescOnboarding.text = page.desc
        holder.binding.imgOnboarding.setImageResource(page.image)
    }

    override fun getItemCount(): Int = pages.size
}