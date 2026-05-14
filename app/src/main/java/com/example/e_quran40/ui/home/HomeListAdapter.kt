package com.example.e_quran40.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.e_quran40.databinding.GridDaftarSuratBinding
import com.example.e_quran40.model.DaftarSuratModel

class HomeListAdapter(daftarSurat: List<DaftarSuratModel.daftarSuratResponse>, private val onClick: (DaftarSuratModel.daftarSuratResponse) -> Unit) : RecyclerView.Adapter<HomeViewModel>() {

    private var list = daftarSurat.toMutableList()
    private var fullList = daftarSurat.toMutableList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewModel {
        val binding = GridDaftarSuratBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return HomeViewModel(binding)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: HomeViewModel, position: Int) {
        val item = list[position]
        holder.bind(item)

        holder.itemView.setOnClickListener {
            onClick(item)
        }
    }

    fun filter(query: String) {
        list = if (query.isEmpty()) {
            fullList
        } else {
            fullList.filter {
                it.nama_latin.contains(query, ignoreCase = true) ||
                        it.arti.contains(query, ignoreCase = true) ||
                        it.nomor.toString().contains(query)
            }.toMutableList()
        }

        notifyDataSetChanged()
    }
}