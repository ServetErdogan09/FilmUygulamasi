package com.example.filmuygulamasi.retrofit.model

data class KategoriModel(
    val genres : List<Kategori>
)

data class Kategori(
    val id : Int ,
    val name : String
)