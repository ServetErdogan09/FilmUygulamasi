package com.example.filmuygulamasi.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.filmuygulamasi.R
import com.example.filmuygulamasi.adapter.GerilimFilmAdapter
import com.example.filmuygulamasi.adapter.AksiyonFilmAdapter
import com.example.filmuygulamasi.adapter.KomediFilmAdapter
import com.example.filmuygulamasi.adapter.BilimKurguFilmAdapter
import com.example.filmuygulamasi.adapter.HaftaninTendAdapter
import com.example.filmuygulamasi.databinding.FragmentAnaSayfaBinding
import com.example.filmuygulamasi.retrofit.model.Movie
import com.example.filmuygulamasi.ui.viewmodel.AnaSayfaViewModel
import com.example.filmuygulamasi.util.gecisYap
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel

@AndroidEntryPoint
class AnaSayfaFragment : Fragment() {
    private val anaSayfaViewModel: AnaSayfaViewModel by viewModels()
    private lateinit var tasarim: FragmentAnaSayfaBinding
    private lateinit var aksiyonAdapter: AksiyonFilmAdapter
    private lateinit var komediFilmAdapter: KomediFilmAdapter
    private lateinit var bilimKurguAdapter: BilimKurguFilmAdapter
    private lateinit var gerilimFilmAdapter: GerilimFilmAdapter
    private lateinit var haftaninTendAdapter: HaftaninTendAdapter
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        tasarim = DataBindingUtil.inflate(inflater, R.layout.fragment_ana_sayfa, container, false)
        swipeRefreshLayout = tasarim.root.findViewById(R.id.swipeRefreshLayout)

        setupRecyclerViews()
        setupSwipeRefresh()

        // LiveData gözlemcileri
        setupFilmObservers()

        // Tıklama olaylarını ayarla
        setupClickListeners()



        return tasarim.root
    }

    private fun setupRecyclerViews() {
        tasarim.AksiyonRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        tasarim.KomediRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        tasarim.BilimKurguRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        tasarim.GerilimRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        tasarim.HaftaninTrendleriRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
    }

    private fun setupSwipeRefresh() {
        swipeRefreshLayout.setOnRefreshListener {
            val genreIds = listOf("28", "35", "878", "27")
            genreIds.forEach { anaSayfaViewModel.turFilmGetir(it)
                Log.e("id","id : $it")
            }

            swipeRefreshLayout.isRefreshing = false
        }
    }

    private fun setupFilmObservers() {
        anaSayfaViewModel.turFilmleri.observe(viewLifecycleOwner) { filmler ->
            Log.d("Filmler", "Filmler sayısı: ${filmler.size}")
            setupFilmAdapters(filmler)
        }

        anaSayfaViewModel.trendFilmGetir()
        anaSayfaViewModel.trendFilm.observe(viewLifecycleOwner) { trendListesi ->
            haftaninTendAdapter = HaftaninTendAdapter(requireContext(), trendListesi)
            Log.d("Filmler", "Filmler trend: ${trendListesi.size}")
            tasarim.HaftaninTrendleriRecyclerView.adapter = haftaninTendAdapter
        }
    }

    private fun setupFilmAdapters(filmler: List<Movie>) {
        val aksiyonFilmler = filmler.filter { it.genre_ids.contains(28) }
        aksiyonAdapter = AksiyonFilmAdapter(requireContext(), aksiyonFilmler)
        Log.d("Filmler", "Filmler Aksiyn: ${aksiyonFilmler.size}")
        tasarim.AksiyonRecyclerView.adapter = aksiyonAdapter

        val komediFilmler = filmler.filter { it.genre_ids.contains(35) }
        komediFilmAdapter = KomediFilmAdapter(requireContext(), komediFilmler)
        Log.d("Filmler", "Filmler Komedi: ${komediFilmler.size}")
        tasarim.KomediRecyclerView.adapter = komediFilmAdapter

        val bilimKurguFilmler = filmler.filter { it.genre_ids.contains(878) }
        bilimKurguAdapter = BilimKurguFilmAdapter(requireContext(), bilimKurguFilmler)
        Log.d("Filmler", "Filmler bilimKurgu: ${bilimKurguFilmler.size}")
        tasarim.BilimKurguRecyclerView.adapter = bilimKurguAdapter

        val gerilimFilmler = filmler.filter { it.genre_ids.contains(27) }
        gerilimFilmAdapter = GerilimFilmAdapter(requireContext(), gerilimFilmler)
        Log.d("Filmler", "Filmler Gerilim : ${gerilimFilmler.size}")
        tasarim.GerilimRecyclerView.adapter = gerilimFilmAdapter
    }

    private fun setupClickListeners() {
        val filmTurleri = mapOf(
            tasarim.textAksiyon to "Aksiyon",
            tasarim.textBilimKurgu to "Bilim-Kurgu",
            tasarim.textMacera to "Macera",
            tasarim.textAnimasyon to "Animasyon",
            tasarim.textKomedi to "Komedi",
            tasarim.textBelgesel to "Belgesel",
            tasarim.textDram to "Dram",
            tasarim.textAile to "Aile",
            tasarim.textFantastik to "Fantastik",
            tasarim.textTarih to "Tarih",
            tasarim.textKorku to "Korku",
            tasarim.textGizem to "Gizem",
            tasarim.textRomantik to "Romantik",
            tasarim.textSavas to "Savaş",
            tasarim.textSuc to "Suç"
        )

        filmTurleri.forEach { (textView, tur) ->
            textView.setOnClickListener {
                val bundle = Bundle()
                bundle.putString("kategori",tur)
                Log.e("secilen","kategori :$tur")
                findNavController().navigate(R.id.secilenKategoriFilmFragment,bundle)
            }
        }
    }
}
