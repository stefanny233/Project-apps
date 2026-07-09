package com.example.project_apps.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.project_apps.data.entity.ReportEntity

@Dao
interface ReportDao {
    @Insert
    suspend fun insert(report: ReportEntity)

    @Query("SELECT * FROM reports ORDER BY timestamp DESC")
    suspend fun getAllReports(): List<ReportEntity>
}