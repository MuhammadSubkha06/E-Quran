package com.example.e_quran40.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.e_quran40.databinding.FragmentDashboardBinding
import com.example.e_quran40.model.JadwalSolatModel
import com.example.e_quran40.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Calendar

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        pilihProvinsi()

        binding.cariJadwal.setOnClickListener {
            val prov = binding.spinnerProvinsi.selectedItem?.toString()
            val kab = binding.spinnerKabupaten.selectedItem?.toString()

            if (!prov.isNullOrEmpty() && !kab.isNullOrEmpty()) {
                fetchJadwalData(prov, kab)
            }
        }
    }

    private fun fetchJadwalData(prov: String, kab: String) {
        val cal = Calendar.getInstance()
        val bulan = cal.get(Calendar.MONTH) + 1
        val tahun = cal.get(Calendar.YEAR)
        val tglSekarang = cal.get(Calendar.DAY_OF_MONTH)

        val request = JadwalSolatModel.ShalatRequest(prov, kab, bulan, tahun)
        val token = "cariJadwal"

        RetrofitClient.retrofit.getJadwalShalat(token, request).enqueue(object : Callback<JadwalSolatModel.ShalatResponse> {
            override fun onResponse
                        (call: Call<JadwalSolatModel.ShalatResponse>,
                         response: Response<JadwalSolatModel.ShalatResponse>
            ) {
                if (response.isSuccessful) {
                    val list = response.body()?.data?.jadwal
                    val dataHariIni = list?.find { it.tanggal_lengkap.contains(String.format("%02d", tglSekarang)) }

                    dataHariIni?.let { updateUI(it) }
                }
            }
            override fun onFailure(call: Call<JadwalSolatModel.ShalatResponse>, t: Throwable) {
                Toast.makeText(requireContext(), "Gagal: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun updateUI(data: JadwalSolatModel.JadwalDetail) {
        binding.apply {
            cardJadwal.visibility = View.VISIBLE
            tvTanggal.text = data.tanggal_lengkap

            layoutSubuh.tvNamaShalat.text = "Subuh"
            layoutSubuh.tvWaktuShalat.text = data.subuh

            layoutDzuhur.tvNamaShalat.text = "Dzuhur"
            layoutDzuhur.tvWaktuShalat.text = data.dzuhur

            layoutAshar.tvNamaShalat.text = "Ashar"
            layoutAshar.tvWaktuShalat.text = data.ashar

            layoutMaghrib.tvNamaShalat.text = "Maghrib"
            layoutMaghrib.tvWaktuShalat.text = data.maghrib

            layoutIsya.tvNamaShalat.text = "Isya"
            layoutIsya.tvWaktuShalat.text = data.isya
        }
    }

    private fun pilihProvinsi() {
        RetrofitClient.retrofit.getProvinsi().enqueue(object : Callback<JadwalSolatModel.ProvinsiResponse> {
            override fun onResponse(
                call: Call<JadwalSolatModel.ProvinsiResponse>,
                response: Response<JadwalSolatModel.ProvinsiResponse>
            ) {
                if (response.isSuccessful) {
                    val list = response.body()?.data ?: emptyList()
                    binding.spinnerProvinsi.adapter = ArrayAdapter(requireContext(),
                        android.R.layout.simple_spinner_dropdown_item,
                        list
                    )
                    binding.spinnerProvinsi.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                        override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                            pilihKabupaten(binding.spinnerProvinsi.selectedItem.toString())
                        }
                        override fun onNothingSelected(p0: AdapterView<*>?) {}
                    }
                }
            }
            override fun onFailure(call: Call<JadwalSolatModel.ProvinsiResponse>, t: Throwable) {}
        })
    }

    private fun pilihKabupaten(prov: String) {
        RetrofitClient.retrofit.getKab(JadwalSolatModel.ProvinsiRequest(prov)).enqueue(object : Callback<JadwalSolatModel.KabkotaResponse> {
            override fun onResponse(
                call: Call<JadwalSolatModel.KabkotaResponse>,
                response: Response<JadwalSolatModel.KabkotaResponse>
            ) {
                if (response.isSuccessful) {
                    binding.spinnerKabupaten.adapter = ArrayAdapter(requireContext(),
                        android.R.layout.simple_spinner_dropdown_item,
                        response.body()?.data ?: emptyList())
                }
            }
            override fun onFailure(call: Call<JadwalSolatModel.KabkotaResponse>, t: Throwable) {}
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}