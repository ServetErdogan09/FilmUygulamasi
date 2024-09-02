package com.example.filmuygulamasi.retrofit.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable


data class FilmCevap(
  val results : MutableList<Movie>
):Serializable

data class Movie(
    val id: Int, // Film ID'si
    val title: String, // Film başlığı
    val overview: String, // Film özeti
    val poster_path: String, // Film posteri yolu
    val release_date: String, // Yayın tarihi
    val vote_average: Double, // Oy ortalaması
    val popularity: Double, // Popülarite puanı
    val backdrop_path: String, // Arka plan resmi yolu
    val genre_ids : List<Int>
): Serializable