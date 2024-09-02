package com.example.filmuygulamasi.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.filmuygulamasi.repo.FilmlerRespository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FilmListesiViewModel @Inject constructor(var fRepo : FilmlerRespository) : ViewModel() {

    val filmListe = fRepo.filmleriGetir

    fun filmGetir(){
        fRepo.eklenenFilmleriGetir()
    }
}