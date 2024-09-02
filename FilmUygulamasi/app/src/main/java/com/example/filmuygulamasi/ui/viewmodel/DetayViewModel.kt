package com.example.filmuygulamasi.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.filmuygulamasi.repo.FilmlerRespository
import com.example.filmuygulamasi.room.RoomModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetayViewModel @Inject constructor(var fRepo : FilmlerRespository) : ViewModel() {
    val kategoriFilm  = fRepo.kategoriler
    val filmEkle = fRepo.filmEklendi
    val filmVar = fRepo.filmVarMi
    val silFilm = fRepo.filmSil

    init {
        kategorileriGetir()
    }

    fun kategorileriGetir(){
        fRepo.kategorileriGetir()
    }

    fun filmKaydet(roomModel: RoomModel){
        fRepo.filmEkle(roomModel)
    }

    fun filmGetir(ad: String){
        fRepo.filmVarMi(ad)
    }

    fun filmSil(filmAd: String){
        fRepo.filmSil(filmAd)
    }


}