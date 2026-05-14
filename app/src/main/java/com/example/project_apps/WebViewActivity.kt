package com.example.project_apps

import android.os.Bundle
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class WebViewActivity : AppCompatActivity() {

    // Gunakan lazy agar inisialisasi dilakukan saat dibutuhkan
    private lateinit var webViewDesa: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)

        // Inisialisasi View
        val toolbarDesa = findViewById<Toolbar>(R.id.toolbar)
        webViewDesa = findViewById(R.id.webView)

        // Setup Toolbar
        setSupportActionBar(toolbarDesa)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
            title = "Web Bina Desa"
        }

        // Ambil URL dari Intent
        val urlFromIntent = intent.getStringExtra("URL_WEB") ?: "http://iyanyow.alwaysdata.net"

        // Setup WebView
        webViewDesa.webViewClient = WebViewClient()
        val settings = webViewDesa.settings
        settings.javaScriptEnabled = true
        settings.domStorageEnabled = true
        settings.allowContentAccess = true
        settings.allowFileAccess = true

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
        }

        webViewDesa.loadUrl(urlFromIntent)
    }

    // Biar kalau di klik back di HP gak langsung nutup activity tapi balik ke halaman web sebelumnya
    override fun onBackPressed() {
        if (webViewDesa.canGoBack()) {
            webViewDesa.goBack()
        } else {
            super.onBackPressed()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}