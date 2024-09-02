package com.example.filmuygulamasi.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.filmuygulamasi.repo.FilmlerRespository
import com.example.filmuygulamasi.room.RoomModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ListDetayViewModel@Inject constructor(var fRepo : FilmlerRespository) : ViewModel() {
    val filmKaydet = fRepo.filmEklendi
    val filmSil = fRepo.filmSil
    val filmVar = fRepo.filmVarMi
    
    fun filmKaydet(roomModel: RoomModel){
        fRepo.filmEkle(roomModel)
    }

    fun filmSil(filmAd: String){
        fRepo.filmSil(filmAd)
    }

    fun filmGetir(ad: String){
        fRepo.filmVarMi(ad)
    }

}