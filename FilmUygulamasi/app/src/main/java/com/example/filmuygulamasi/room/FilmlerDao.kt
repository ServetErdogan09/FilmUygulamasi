package com.example.filmuygulamasi.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FilmlerDao {
    @Insert
   suspend fun filmEkle(roomModel: RoomModel)

   @Query("SELECT * FROM film")
   suspend fun filimleriGetir() : List<RoomModel>

   @Query("SELECT * FROM film WHERE title = :filmAd LIMIT 1")
   suspend fun filmVarMi(filmAd: String): RoomModel?

   @Query("DELETE FROM film WHERE title =:filmAd")
   suspend fun filmSil(filmAd: String): Int
}