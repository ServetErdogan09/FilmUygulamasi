package com.example.filmuygulamasi.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.filmuygulamasi.repo.FilmlerRespository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

// hilt sayesinde buraya bağımlı olduğumuz sınıfları dışardan kolaylıkla verebiliriz dışardan  FilmlerRespository alacağım
// viewmodel sınıfların hepsine HiltViewModel olduğunu belirtim ve  @Inject constructor la  dışardan  FilmlerRespository istediğimi belirtim
@HiltViewModel
class AnaSayfaViewModel @Inject constructor(var fRepo : FilmlerRespository) : ViewModel() {
    val kategoriId  = fRepo.kategoriler
    val turFilmleri = fRepo.mutableLiveData
    val trendFilm = fRepo.mutableLiveData
    init {
        kategorileriGetir()
    }

    fun kategorileriGetir(){
        fRepo.kategorileriGetir()
    }

    fun turFilmGetir(id : String){
        fRepo.kategoriFilmGetir(id)

    }

    fun trendFilmGetir(){
        fRepo.trendFilimler()
    }

    fun secilenFilmleriGetir(kategoriAdi : String){
        fRepo.secilenKategoriFilmleriGetir(kategoriAdi)
    }


}