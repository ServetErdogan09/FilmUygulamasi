package com.example.filmuygulamasi.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.filmuygulamasi.repo.FilmlerRespository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AramaViewModel  @Inject constructor(var fRepo : FilmlerRespository) : ViewModel() {

    val liste = fRepo.filmAraList

    fun ara(filmAd : String){
        fRepo.filmAra(filmAd)
    }

}