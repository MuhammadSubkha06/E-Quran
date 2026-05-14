package com.example.e_quran40.network

import com.example.e_quran40.model.DaftarSuratModel
import com.example.e_quran40.model.DetailSurat
import com.example.e_quran40.model.JadwalSolatModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiClient {

    @GET("api/surat")
    fun getDaftar(): Call<List<DaftarSuratModel.daftarSuratResponse>>

    @GET("api/surat/{id}")
    fun getDetailSurat(@Path("id") id: Int): Call<DetailSurat.DetailSuratResponse>

    @GET("api/v2/shalat/provinsi")
    fun getProvinsi(): Call<JadwalSolatModel.ProvinsiResponse>

    @POST("api/v2/shalat/kabkota")
    fun getKab(@Body request: JadwalSolatModel.ProvinsiRequest): Call<JadwalSolatModel.KabkotaResponse>

    @POST("api/v2/shalat")
    fun getJadwalShalat(@Header("Authorization") token: String, @Body request: JadwalSolatModel.ShalatRequest): Call<JadwalSolatModel.ShalatResponse>
}