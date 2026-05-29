package com.example.project_apps

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide // Pastikan sudah sync Glide di build.gradle

// 1. Blueprint data (Model) - Variabel disesuaikan dengan MoreFragment & dukung URL Gambar
data class BinaDesaModel(
    val nama: String,
    val deskripsi: String,
    val gambar: String // Menyimpan link URL gambar internet
)

// 2. Kelas Adapter Utama untuk mengatur RecyclerView Grid 2 Kolom kamu
class BinaDesaAdapter(private val listData: List<BinaDesaModel>) :
    RecyclerView.Adapter<BinaDesaAdapter.ViewHolder>() {

    // Menghubungkan komponen UI di dalam item_bina_desa.xml
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        // ID di sesuaikan dengan item_bina_desa.xml yang kita buat sebelumnya
        val imgItem: ImageView = view.findViewById(R.id.imgItem)
        val tvNama: TextView = view.findViewById(R.id.tvNamaItem)
        val tvDesc: TextView = view.findViewById(R.id.tvDescItem)
    }

    // Membuat cetakan baris list/grid baru
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_bina_desa, parent, false)
        return ViewHolder(view)
    }

    // Memasukkan data dummy ke dalam komponen UI (Teks & Gambar dari internet)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = listData[position]
        holder.tvNama.text = data.nama
        holder.tvDesc.text = data.deskripsi

        // Menggunakan Glide untuk mendownload gambar otomatis dari link URL internet
        Glide.with(holder.itemView.context)
            .load(data.gambar) // Membaca URL String (https://picsum.photos/...)
            .placeholder(android.R.drawable.ic_menu_gallery) // Gambar loading sementara
            .error(android.R.drawable.ic_menu_report_image) // Gambar jika internet error
            .into(holder.imgItem)
    }

    // Menghitung jumlah total data yang mau ditampilkan
    override fun getItemCount(): Int = listData.size
}