package com.example.e_quran40.ui.home

import androidx.recyclerview.widget.RecyclerView
import com.example.e_quran40.databinding.GridDaftarSuratBinding
import com.example.e_quran40.model.DaftarSuratModel

class HomeViewModel(private val binding: GridDaftarSuratBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(data: DaftarSuratModel.daftarSuratResponse){
        binding.txtNomor.text = data.nomor.toString()
        binding.txtNama.text = data.nama
        binding.txtNamaLatin.text = data.nama_latin
        binding.txtTempatTurun.text = data.tempat_turun
        binding.txtJumlahAyat.text = "${data.jumlah_ayat} Ayat"
    }

}