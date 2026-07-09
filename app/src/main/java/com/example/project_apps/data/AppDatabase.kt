package com.example.project_apps.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.project_apps.data.dao.NoteDao
import com.example.project_apps.data.dao.ReportDao
import com.example.project_apps.data.entity.NoteEntity
import com.example.project_apps.data.entity.ReportEntity

@Database(
    entities = [NoteEntity::class, ReportEntity::class], // 🚀 Langsung daftarkan kedua tabel di sini mase
    version = 1
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun noteDao(): NoteDao
    abstract fun reportDao(): ReportDao // 🚀 Pintu akses untuk query laporan pengaduan

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                )
                    // 🚀 Jaga-jaga jika nanti mase utak-atik struktur tabel di tengah jalan agar tidak crash
                    .fallbackToDestructiveMigration()
                    .build().also { INSTANCE = it }
            }
        }
    }
}