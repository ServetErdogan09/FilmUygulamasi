package com.example.filmuygulamasi.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.filmuygulamasi.R
import com.example.filmuygulamasi.databinding.FragmentFilmListesiFragmentBinding
import com.example.filmuygulamasi.databinding.FragmentListeDetayBinding
import com.example.filmuygulamasi.retrofit.model.Movie
import com.example.filmuygulamasi.room.RoomModel
import com.example.filmuygulamasi.ui.viewmodel.ListDetayViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListeDetayFragment : Fragment() {
    private  val args : ListeDetayFragmentArgs by navArgs()
    private lateinit var tasarim : FragmentListeDetayBinding
    private val listeDetayViewModel: ListDetayViewModel by viewModels()
    private var tur : String? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
     tasarim = DataBindingUtil.inflate(inflater,R.layout.fragment_liste_detay,container,false)

        tasarim.filmNesnesi = args.filmList
        tur = args.filmList.title

        setupEkleCikarmaIslemleri()

        val bottomNa = requireActivity().findViewById<BottomNavigationView>(R.id.bottomNav)
        bottomNa.visibility = View.GONE

        tasarim.detayText.setOnClickListener {
            if (tasarim.ozetText.visibility == View.GONE){
                tasarim.ozetText.visibility = View.VISIBLE

            }else{
                tasarim.ozetText.visibility = View.GONE
            }
        }

        val baseUrl = "https://image.tmdb.org/t/p/w500"
        val postUrl = baseUrl + args.filmList.backdrop_path

        Picasso.get()
            .load(postUrl)
            .resize(200,200)
            .centerCrop()
            .into(tasarim.arkaPlanResmi)


        return tasarim.root
    }

    override fun onDestroyView() {
        val bottomNa = requireActivity().findViewById<BottomNavigationView>(R.id.bottomNav)
        bottomNa.visibility = View.VISIBLE
        super.onDestroyView()
    }

    private fun toggleEkleCikarma(isEklendi : Boolean){
        tasarim.beyazEkle.visibility = if (isEklendi) View.GONE else View.VISIBLE
        tasarim.sariEkle.visibility = if (isEklendi) View.VISIBLE else View.GONE
    }

    private fun setupEkleCikarmaIslemleri() {
        val tiklananFilm = args.filmList
        if (tur != null) {
            val eklenenFilm = RoomModel(
                0,
                tiklananFilm.title,
                tiklananFilm.overview,
                tiklananFilm.poster_path,
                tiklananFilm.release_date,
                tiklananFilm.vote_average,
                tiklananFilm.popularity,
                tiklananFilm.backdrop_path,
                tur!!
            )

            listeDetayViewModel.filmGetir(eklenenFilm.title)
            listeDetayViewModel.filmVar.observe(viewLifecycleOwner) { mevcutFilm ->
                if (mevcutFilm == null) {
                    // Film veri tabanında mevcut değil, ekleyebiliriz

                    tasarim.beyazEkle.visibility = View.VISIBLE
                    tasarim.sariEkle.visibility = View.GONE

                    tasarim.beyazEkle.setOnClickListener {
                        listeDetayViewModel.filmKaydet(eklenenFilm)
                        toggleEkleCikarma(isEklendi = true)
                        toastMesaj("İzleme listene eklendi")
                    }
                } else {
                    // Film veri tabanında mevcut, çıkarma işlemi yapılabilir
                    tasarim.beyazEkle.visibility = View.GONE
                    tasarim.sariEkle.visibility = View.VISIBLE

                    tasarim.sariEkle.setOnClickListener {
                        // veri silme
                        listeDetayViewModel.filmSil(eklenenFilm.title)
                        toggleEkleCikarma(isEklendi = false)
                        toastMesaj("İzleme listenden çıkarıldı")
                    }
                }
            }
            // Devamında diğer işlemler
        } else {
            Log.e("DetayFragment", "tur değeri null, işlem yapılamıyor")
        }
    }

    private fun toastMesaj(mesaj : String){

        val inflater = layoutInflater
        val layout = inflater.inflate(R.layout.toast_tasarim, null)

        val textView = layout.findViewById<TextView>(R.id.toastMesaj)

        textView.text = mesaj  // Dinamik toast mesajı
        val toast = Toast(requireContext())
        toast.setGravity(Gravity.BOTTOM or Gravity.FILL_HORIZONTAL,0,0)
        toast.duration = Toast.LENGTH_SHORT
        toast.view = layout
        toast.show()
    }

}