package com.example.filmuygulamasi.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.filmuygulamasi.retrofit.model.FilmCevap

@Database(entities = [RoomModel::class], version = 1)
abstract class Veritabani : RoomDatabase() {
    abstract fun getFilimler() : FilmlerDao
}