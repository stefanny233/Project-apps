package com.example.project_apps

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.example.project_apps.data.AppDatabase
import com.example.project_apps.data.entity.NoteEntity
import com.example.project_apps.data.entity.ReportEntity
import com.example.project_apps.databinding.FragmentTabForumReportBinding
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.qrcode.QRCodeWriter
import kotlinx.coroutines.launch

class TabForumReportFragment : Fragment() {

    private var _binding: FragmentTabForumReportBinding? = null
    private val binding get() = _binding!!

    private val sharedViewModel: SharedReportViewModel by activityViewModels()
    private lateinit var db: AppDatabase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTabForumReportBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        db = AppDatabase.getInstance(requireContext())

        binding.btnSubmitReport.setOnClickListener {
            val name = binding.edtReporterName.text.toString().trim()
            val content = binding.edtReportContent.text.toString().trim()

            if (name.isEmpty() || content.isEmpty()) {
                Toast.makeText(context, "Harap lengkapi semua data!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val qrPayload = "Pelapor : $name\nKeluhan : $content"
            val qrBitmap = createQR(qrPayload)
            binding.ivReportQrCode.setImageBitmap(qrBitmap)
            sharedViewModel.kirimLaporanKeHistory(name, content)

            viewLifecycleOwner.lifecycleScope.launch {
                val waktuSekarang = System.currentTimeMillis()

                val laporanBaru = ReportEntity(
                    name = name,
                    content = content,
                    timestamp = waktuSekarang
                )
                db.reportDao().insert(laporanBaru)

                val catatanOtomatis = NoteEntity(
                    title = "Laporan: $name",
                    content = content,
                    createdAt = waktuSekarang
                )
                db.noteDao().insert(catatanOtomatis)
            }

            val intentKeRiwayat = Intent(
                requireContext(),
                MainActivity::class.java
            )
            com.example.project_apps.utils.NotificationHelper.showNotification(
                context = requireContext(),
                title = "📝 Pengaduan Sukses Terkirim",
                message = "Halo $name, laporan Anda mengenai '$content' berhasil terdaftar di database desa.",
                intent = intentKeRiwayat
            )

            Toast.makeText(context, "Laporan terkirim, QR dibuat & Masuk ke Catatan!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun createQR(text: String): Bitmap {
        val writer = QRCodeWriter()
        val matrix = writer.encode(
            text,
            BarcodeFormat.QR_CODE,
            500,
            500,
            mapOf(EncodeHintType.CHARACTER_SET to "UTF-8")
        )
        return Bitmap.createBitmap(500, 500, Bitmap.Config.RGB_565).apply {
            for (x in 0 until 500) {
                for (y in 0 until 500) {
                    setPixel(x, y, if (matrix.get(x, y)) Color.BLACK else Color.WHITE)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}