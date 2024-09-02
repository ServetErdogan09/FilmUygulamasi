package com.example.filmuygulamasi.ui.viewmodel


import androidx.lifecycle.ViewModel
import com.example.filmuygulamasi.repo.FilmlerRespository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class PopulerViewModel @Inject constructor(var fRepo : FilmlerRespository)  : ViewModel(){


    val populerFilmler  = fRepo.mutableLiveData
    val yuklendiMi  = fRepo.yuklendiMi

     fun populerfilmGetir(page: Int) {
        fRepo.populerFilimleriGetir(page)



    }



}