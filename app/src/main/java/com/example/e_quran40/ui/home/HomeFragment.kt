package com.example.e_quran40.ui.home

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.e_quran40.R
import com.example.e_quran40.databinding.FragmentHomeBinding
import com.example.e_quran40.model.DaftarSuratModel
import com.example.e_quran40.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: HomeListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        binding.rvDaftarSurat.layoutManager =
            LinearLayoutManager(requireContext())

        tampilDaftarSurat()

        return binding.root
    }

    private fun tampilDaftarSurat() {

        RetrofitClient.retrofit.getDaftar()
            .enqueue(object :
                Callback<List<DaftarSuratModel.daftarSuratResponse>> {

                override fun onResponse(
                    call: Call<List<DaftarSuratModel.daftarSuratResponse>>,
                    response: Response<List<DaftarSuratModel.daftarSuratResponse>>
                ) {

                    if (response.isSuccessful) {

                        val listData = response.body()

                        if (listData != null) {

                            adapter = HomeListAdapter(listData) { surat ->
                                val bundle = Bundle()
                                bundle.putInt("id_surat", surat.nomor)

                                findNavController().navigate(
                                    R.id.action_navigation_home_to_detailFragment,
                                    bundle
                                )
                            }

                            binding.rvDaftarSurat.adapter = adapter

                            cariSurat()
                        }
                    }
                }

                override fun onFailure(
                    call: Call<List<DaftarSuratModel.daftarSuratResponse>>,
                    t: Throwable
                ) {
                    Toast.makeText(
                        requireContext(),
                        "Error: ${t.localizedMessage}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            })
    }

    private fun cariSurat() {

        binding.etSearch.addTextChangedListener(object : TextWatcher {

            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {}

            override fun onTextChanged(
                s: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) {
                adapter.filter(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}