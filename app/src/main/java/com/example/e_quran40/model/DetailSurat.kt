package com.example.e_quran40.model

class DetailSurat{
    data class DetailSuratResponse(
    val nomor: Int,
    val nama: String,
    val nama_latin: String,
    val jumlah_ayat: Int,
    val tempat_turun: String,
    val arti: String,
    val deskripsi: String,
    val audio: String,
    val status: Boolean,
    val ayat: List<Ayat>,
)

    data class Ayat(
        val id: Int,
        val surah: Int,
        val nomor: Int,
        val ar: String,
        val tr: String,
        val idn: String
    )
}
