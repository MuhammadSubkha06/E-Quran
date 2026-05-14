package com.example.e_quran40.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.e_quran40.databinding.FragmentDetailBinding
import com.example.e_quran40.model.DetailSurat
import com.example.e_quran40.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    private var idSurat: Int = 1
    private var player: ExoPlayer? = null
    private var audioSurat: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        idSurat = arguments?.getInt("id_surat") ?: 1
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentDetailBinding.inflate(inflater, container, false)

        binding.rvDetail.layoutManager = LinearLayoutManager(requireContext())
        player = ExoPlayer.Builder(requireContext()).build()
        binding.playerView.player = player
        player?.playWhenReady = false

        getDetailSurat(idSurat)

        return binding.root
    }

    private fun getDetailSurat(id: Int) {

        RetrofitClient.retrofit.getDetailSurat(id)
            .enqueue(object : Callback<DetailSurat.DetailSuratResponse> {

                override fun onResponse(
                    call: Call<DetailSurat.DetailSuratResponse>,
                    response: Response<DetailSurat.DetailSuratResponse>
                ) {
                    if (response.isSuccessful) {

                        val detail = response.body()

                        if (detail != null) {

                            audioSurat = detail.audio
                            binding.tvNamaSurat.text = detail.nama ?: detail.nama
                            val ayatList = detail.ayat.toMutableList()
                            binding.rvDetail.adapter =
                                DetailAdapter(
                                    ayatList,
                                    idSurat
                                )

                            val mediaItem =
                                MediaItem.fromUri(audioSurat)

                            player?.setMediaItem(mediaItem)
                            player?.prepare()
                        }

                    } else {
                        Toast.makeText(
                            requireContext(),
                            "Error code: ${response.code()}",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }

                override fun onFailure(
                    call: Call<DetailSurat.DetailSuratResponse>,
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

    override fun onDestroyView() {
        super.onDestroyView()
        player?.release()
        player = null
        _binding = null
    }
}