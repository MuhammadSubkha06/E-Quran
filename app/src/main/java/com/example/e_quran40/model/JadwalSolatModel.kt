package com.example.e_quran40.model

class JadwalSolatModel {
    data class ProvinsiResponse(
        val code: Int,
        val message: String,
        val data: List<String>
    )

    data class KabkotaResponse(
        val code: Int,
        val message: String,
        val data: List<String>
    )

    data class ProvinsiRequest(
        val provinsi : String
    )

    data class ShalatRequest(
        val provinsi: String,
        val kabkota: String,
        val bulan: Int? = null,
        val tahun: Int? = null
    )

    data class ShalatResponse(
        val code: Int,
        val message: String,
        val data: ShalatData?
    )

    data class ShalatData(
        val jadwal: List<JadwalDetail>
    )

    data class JadwalDetail(
        val tanggal_lengkap: String,
        val subuh: String,
        val dzuhur: String,
        val ashar: String,
        val maghrib: String,
        val isya: String
    )
}