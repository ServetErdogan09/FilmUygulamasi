package com.example.filmuygulamasi.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.filmuygulamasi.databinding.KategoriDuzenRecyclerBinding
import com.example.filmuygulamasi.retrofit.model.Movie
import com.example.filmuygulamasi.ui.fragment.AnaSayfaFragmentDirections
import com.example.filmuygulamasi.util.gecisYap
import com.squareup.picasso.Picasso

class HaftaninTendAdapter(val mContext: Context, val filmListesi : List<Movie>) :
    RecyclerView.Adapter<HaftaninTendAdapter.KategoriFilmTutucu>() {

    class KategoriFilmTutucu(val tasarim : KategoriDuzenRecyclerBinding) : RecyclerView.ViewHolder(tasarim.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KategoriFilmTutucu {
        val tasarim = KategoriDuzenRecyclerBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return KategoriFilmTutucu(tasarim)
    }

    override fun getItemCount(): Int {
       return filmListesi.size
    }

    override fun onBindViewHolder(holder: KategoriFilmTutucu, position: Int) {

        val kategoriFilm = filmListesi[position]

        val baseUrl = "https://image.tmdb.org/t/p/w500"
        val posterUrl = baseUrl + kategoriFilm.poster_path

        Picasso.get()
            .load(posterUrl)
            .resize(200,200)
            .centerCrop()
            .into(holder.tasarim.resim)

        holder.tasarim.textImdb.text = "IMDB :${kategoriFilm.vote_average.toString().substring(0,3)}"
        holder.tasarim.filmTarih.text = "${kategoriFilm.release_date}"
        holder.tasarim.filmBaslik.text = kategoriFilm.title

        holder.tasarim.resim.setOnClickListener {
            val detayGecis = AnaSayfaFragmentDirections.actionAnaSayfaFragmentToDetayFragment(filmGecis = kategoriFilm,"")
            Navigation.gecisYap(it,detayGecis)
        }



    }
}