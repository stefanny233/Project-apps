package com.example.project_apps

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedReportViewModel : ViewModel() {

    private val _laporanBaru = MutableLiveData<Pair<String, String>>()
    val laporanBaru: LiveData<Pair<String, String>> get() = _laporanBaru

    fun kirimLaporanKeHistory(nama: String, keluhan: String) {
        _laporanBaru.value = Pair(nama, keluhan)
    }
}