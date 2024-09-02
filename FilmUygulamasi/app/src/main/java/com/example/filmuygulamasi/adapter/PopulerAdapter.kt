package com.example.filmuygulamasi.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.filmuygulamasi.databinding.PopulerDuzenRecyclerBinding
import com.example.filmuygulamasi.retrofit.model.Movie
import com.example.filmuygulamasi.ui.fragment.PopulerFragmentDirections
import com.example.filmuygulamasi.util.gecisYap
import com.squareup.picasso.Picasso
import kotlin.math.floor

class PopulerAdapter(private val mContext: Context, private val filmListe: MutableList<Movie>) : RecyclerView.Adapter<PopulerAdapter.FilmTutucu>() {

    inner class FilmTutucu(val tasarim: PopulerDuzenRecyclerBinding) : RecyclerView.ViewHolder(tasarim.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmTutucu {
        val tasarim = PopulerDuzenRecyclerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FilmTutucu(tasarim)
    }

    override fun getItemCount(): Int {
        return filmListe.size
    }

    override fun onBindViewHolder(holder: FilmTutucu, position: Int) {
        val film = filmListe[position]


        // API'den aldığınız poster_path için base URL'yi eklemelisiniz
        val baseUrl = "https://image.tmdb.org/t/p/w500"
        val posterUrl = baseUrl + film.poster_path

        Picasso.get()
            .load(posterUrl)
            .resize(200, 200)
            .centerCrop()
            .into(holder.tasarim.filmResim)

        holder.tasarim.filmIsmi.text = film.title
        val sayi = film.vote_average
        val sayiDouble = sayi.toDouble()
        val yuvarlanmisSayi = floor(sayiDouble * 10) / 10
        holder.tasarim.kullaniciOylamasi.text = yuvarlanmisSayi.toString()



        holder.tasarim.filmResim.setOnClickListener {
            val filmGecis = PopulerFragmentDirections.populertDetayGecis(film,"")
            // Extensions kullandık
            Navigation.gecisYap(it,filmGecis)
        }
    }


    fun addMovies(newMovies: List<Movie>) {
        val startPosition = filmListe.size
        filmListe.addAll(newMovies)
        notifyItemRangeInserted(startPosition, newMovies.size)
    }
}
