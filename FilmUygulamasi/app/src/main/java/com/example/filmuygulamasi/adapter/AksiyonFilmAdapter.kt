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

class AksiyonFilmAdapter(val mContext: Context, val aksiyonListe : List<Movie>) : RecyclerView.Adapter<AksiyonFilmAdapter.AksiyonFilmTutucu>() {

    class AksiyonFilmTutucu(val tasarim : KategoriDuzenRecyclerBinding) : RecyclerView.ViewHolder(tasarim.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AksiyonFilmTutucu {
        val tasarim = KategoriDuzenRecyclerBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return AksiyonFilmTutucu(tasarim)
    }

    override fun getItemCount(): Int {
        return aksiyonListe.size
    }

    override fun onBindViewHolder(holder: AksiyonFilmTutucu, position: Int) {
        val kategoriFilm = aksiyonListe[position]

        val baseUrl = "https://image.tmdb.org/t/p/w500"
        val posterUrl = baseUrl + kategoriFilm.poster_path

        Picasso.get()
            .load(posterUrl)
            .resize(250,250)
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