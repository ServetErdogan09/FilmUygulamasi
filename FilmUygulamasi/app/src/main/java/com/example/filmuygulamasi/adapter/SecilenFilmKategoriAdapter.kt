package com.example.filmuygulamasi.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.filmuygulamasi.databinding.SecilenKategoriDuzenRecyclerBinding
import com.example.filmuygulamasi.retrofit.model.Movie
import com.example.filmuygulamasi.ui.fragment.SecilenKategoriFilmFragmentDirections
import com.example.filmuygulamasi.util.gecisYap
import com.squareup.picasso.Picasso

class SecilenFilmKategoriAdapter(val mContext: Context, val secilenFilm : List<Movie>) : RecyclerView.Adapter<SecilenFilmKategoriAdapter.KategoriTutucu>() {
    class KategoriTutucu(val tasarim : SecilenKategoriDuzenRecyclerBinding) : RecyclerView.ViewHolder(tasarim.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KategoriTutucu {
        val tasarim = SecilenKategoriDuzenRecyclerBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return KategoriTutucu(tasarim)
    }

    override fun getItemCount(): Int {

        return secilenFilm.size
    }

    override fun onBindViewHolder(holder: KategoriTutucu, position: Int) {

        val film = secilenFilm[position]

        val baseUrl = "https://image.tmdb.org/t/p/w500"
        val posterUrl = baseUrl + film.poster_path

        Picasso.get()
            .load(posterUrl)
            .resize(150,150)
            .centerCrop()
            .into(holder.tasarim.filmImageView)


        holder.tasarim.filmImageView.setOnClickListener {
            val gonderilenDeger = "secilenKategori"
            val gecis = SecilenKategoriFilmFragmentDirections.actionSecilenKategoriFilmFragmentToDetayFragment(film,gonderilenDeger)
            Navigation.gecisYap(it,gecis)
        }

    }
}