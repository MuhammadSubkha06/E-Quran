package com.example.e_quran40.ui.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.e_quran40.databinding.DetailAyatBinding
import com.example.e_quran40.databinding.ItemHeaderBinding
import com.example.e_quran40.model.DetailSurat

class DetailAdapter(
    private val listAyat: List<DetailSurat.Ayat>,
    private val idSurat: Int
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val TYPE_HEADER = 0
        private const val TYPE_AYAT = 1
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0 && idSurat != 9) {
            TYPE_HEADER
        } else {
            TYPE_AYAT
        }
    }

    override fun getItemCount(): Int {
        return if (idSurat != 9) {
            listAyat.size + 1
        } else {
            listAyat.size
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            : RecyclerView.ViewHolder {

        return if (viewType == TYPE_HEADER) {
            val binding = ItemHeaderBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            HeaderViewHolder(binding)
        } else {
            val binding = DetailAyatBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            AyatViewHolder(binding)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (holder is HeaderViewHolder) {
            holder.bind()
        } else if (holder is AyatViewHolder) {

            val realPosition =
                if (idSurat != 9) position - 1 else position

            holder.bind(listAyat[realPosition])
        }
    }


    inner class HeaderViewHolder(
        private val binding: ItemHeaderBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind() {
            binding.tvTaawudz.setTextColor(android.graphics.Color.WHITE)
            binding.tvTaawudzArtinya.setTextColor(android.graphics.Color.parseColor("#CCCCCC"))
            binding.tvBismillah.setTextColor(android.graphics.Color.WHITE)
            binding.tvBismillahArtinya.setTextColor(android.graphics.Color.parseColor("#CCCCCC"))

            binding.tvTaawudz.text = "أَعُوذُ بِاللَّهِ مِنَ الشَّيْطَانِ الرَّجِيمِ"
            binding.tvTaawudzArtinya.text = "Aku berlindung kepada Allah dari setan yang terkutuk."
            binding.tvBismillah.text = "بِسْمِ اللّٰهِ الرَّحْمٰنِ الرَّحِيْمِ"
            binding.tvBismillahArtinya.text = "Dengan nama Allah Yang Maha Pengasih lagi Maha Penyayang."
        }
    }


    inner class AyatViewHolder(
        private val binding: DetailAyatBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(data: DetailSurat.Ayat) {
            binding.tvNomor.text = data.nomor.toString()
            binding.tvArab.text = data.ar
            binding.tvLatin.text = data.tr
            binding.tvTerjemah.text = data.idn
        }
    }
}