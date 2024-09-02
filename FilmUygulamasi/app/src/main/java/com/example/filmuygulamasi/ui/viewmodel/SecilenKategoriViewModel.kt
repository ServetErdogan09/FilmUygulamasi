package com.example.filmuygulamasi.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.filmuygulamasi.repo.FilmlerRespository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SecilenKategoriViewModel @Inject constructor(var fRepo : FilmlerRespository)  : ViewModel() {

    val secilenFilm = fRepo.secileniFilmGetir

    fun filmKategorisiGetir(kategoriAdi: String){
        fRepo.secilenKategoriFilmleriGetir(kategoriAdi)
    }
}