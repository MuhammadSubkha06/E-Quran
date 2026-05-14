package com.example.e_quran40.model

class DaftarSuratModel {
    data class daftarSuratResponse(
        val nomor : Int = 0,
        val nama : String = "",
        val nama_latin : String = "",
        val jumlah_ayat : Int = 0,
        val tempat_turun : String = "",
        val arti : String = "",
        val deskripsi : String = ""
    )
}