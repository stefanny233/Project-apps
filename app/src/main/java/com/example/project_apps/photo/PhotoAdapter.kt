package com.example.project_apps.home.photo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.project_apps.data.model.PhotoModel
import com.example.project_apps.databinding.ItemPhotoBinding

class PhotoAdapter(private val items: List<PhotoModel>) :
    RecyclerView.Adapter<PhotoAdapter.PhotoViewHolder>() {

    // Struktur model lokal untuk standarisasi teks berita pedesaan
    private data class BeritaDesa(val judul: String, val deskripsi: String)

    // Data array 10 Kegiatan Nyata Sektor Administrasi & Lapangan Bina Desa
    private val daftarBerita = listOf(
        BeritaDesa("Penyuluhan Tani Modern", "Pelatihan sistem irigasi tetes untuk kelompok tani kelapa sawit."),
        BeritaDesa("Gotong Royong Akbar", "Warga kompak membersihkan area fasilitas umum dan drainase desa."),
        BeritaDesa("Layanan Rutin Posyandu", "Pemeriksaan tumbuh kembang balita dan pemberian vitamin gratis."),
        BeritaDesa("Musyawarah Desa (Musrenbang)", "Rapat koordinasi warga membahas alokasi dana desa tahun ini."),
        BeritaDesa("Pemberdayaan UMKM PKK", "Pelatihan pembuatan keripik singkong kemasan bernilai jual tinggi."),
        BeritaDesa("Penyaluran Bansos Sembako", "Distribusi bantuan pangan pokok bagi keluarga pra-sejahtera."),
        BeritaDesa("Perbaikan Irigasi Sawah", "Pembangunan beton saluran air demi kelancaran musim tanam warga."),
        BeritaDesa("Workshop Desa Digital", "Edukasi warga mengenai pemanfaatan sistem layanan online desa."),
        BeritaDesa("Pentas Seni & Budaya", "Festival tahunan guna melestarikan adat istiadat leluhur desa."),
        BeritaDesa("Siskamling Siaga Malam", "Peningkatan jadwal ronda malam demi menjaga keamanan lingkungan.")
    )

    inner class PhotoViewHolder(val binding: ItemPhotoBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val binding = ItemPhotoBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return PhotoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        val item = items[position]

        // Pola sisa bagi (%) memastikan data teks berputar aman tanpa risiko IndexOutOfBounds
        val berita = daftarBerita[position % daftarBerita.size]

        // Ekstrak nama depan author API Picsum sebagai identitas nama desanya mase
        val namaDesa = "Desa ${item.author.split(" ").first()}"

        // Set ke komponen layout item
        holder.binding.tvAuthor.text = "${berita.judul}\n($namaDesa)"
        holder.binding.tvLocation.text = berita.deskripsi

        // Tembak URL Picsum khusus pencarian kategori alam, pedesaan, tani, dan warga lokal
        val ruralImageUrl = "https://picsum.photos/400/300?random=${item.id}&nature,village,agriculture,people"

        Glide.with(holder.itemView.context)
            .load(ruralImageUrl)
            .placeholder(android.R.color.darker_gray)
            .centerCrop()
            .into(holder.binding.imgPhoto)
    }

    override fun getItemCount(): Int = items.size
}