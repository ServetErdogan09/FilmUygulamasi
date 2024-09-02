package com.example.filmuygulamasi.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity("film")
data class RoomModel(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,  // Film ID'si
    val title: String, // Film başlığı
    val overview: String, // Film özeti
    val poster_path: String, // Film posteri yolu
    val release_date: String, // Yayın tarihi
    val vote_average: Double, // Oy ortalaması
    val popularity: Double, // Popülarite puanı
    val backdrop_path: String, // Arka plan resmi yolu
    val kategoriText : String
  //  val gorunurluk : String
) : Serializable