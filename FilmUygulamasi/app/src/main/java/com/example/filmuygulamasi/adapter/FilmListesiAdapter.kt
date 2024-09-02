package com.example.filmuygulamasi.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.filmuygulamasi.databinding.FilmListesiDuzenBinding
import com.example.filmuygulamasi.room.RoomModel
import com.example.filmuygulamasi.ui.fragment.FilmListesiFragmentDirections
import com.example.filmuygulamasi.util.gecisYap
import com.squareup.picasso.Picasso

class FilmListesiAdapter(val mContext : Context , val filmList : List<RoomModel>) : RecyclerView.Adapter<FilmListesiAdapter.FilmListesiTutucu>() {

    class FilmListesiTutucu(val tasarim : FilmListesiDuzenBinding) :RecyclerView.ViewHolder(tasarim.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmListesiTutucu {
        val tasarim= FilmListesiDuzenBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return FilmListesiTutucu(tasarim)
    }

    override fun getItemCount(): Int {

        return filmList.size

    }

    override fun onBindViewHolder(holder: FilmListesiTutucu, position: Int) {

        val film = filmList[position]

        val baseUrl = "https://image.tmdb.org/t/p/w500"
        val posterUrl = baseUrl + film.poster_path

        Picasso.get()
            .load(posterUrl)
            .resize(150,150)
            .centerCrop()
            .into(holder.tasarim.resim)

        holder.tasarim.filmBaslik.text = film.title
        holder.tasarim.filmTarih.text = film.release_date
        val imdb = film.vote_average.toString()

        val impdPuan = imdb.substring(0,3)
        holder.tasarim.textImdb.text = impdPuan

        holder.tasarim.cardView.setOnClickListener {
            val gecis = FilmListesiFragmentDirections.actionFilmListesiFragmentFragmentToListeDetayFragment(film)
            Navigation.gecisYap(it,gecis)
        }
    }
}