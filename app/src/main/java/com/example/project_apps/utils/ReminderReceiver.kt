package com.example.project_apps.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.project_apps.MainActivity // Mengarah kembali ke halaman utama ber-tab mase

class ReminderReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val title = intent.getStringExtra("title") ?: "Pengingat Desa"
        val message = intent.getStringExtra("message") ?: "Waktunya melakukan agenda desa!"

        val targetIntent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        NotificationHelper.showNotification(
            context = context,
            title = title,
            message = message,
            intent = targetIntent
        )
    }
}