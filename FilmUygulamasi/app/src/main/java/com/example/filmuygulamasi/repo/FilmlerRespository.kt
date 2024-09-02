package com.example.filmuygulamasi.repo

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.filmuygulamasi.retrofit.model.Movie
import com.example.filmuygulamasi.retrofit.FilmClient
import com.example.filmuygulamasi.retrofit.model.Kategori
import com.example.filmuygulamasi.room.FilmlerDao
import com.example.filmuygulamasi.room.RoomModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class FilmlerRespository(var fDao : FilmlerDao) {

    val mutableLiveData = MutableLiveData<List<Movie>>()
    val secileniFilmGetir = MutableLiveData<List<Movie>>()
    val kategoriler = MutableLiveData<List<Kategori>>()
    val yuklendiMi = MutableLiveData<Boolean>()
    val filmEklendi = MutableLiveData<RoomModel>()
    val filmleriGetir = MutableLiveData<List<RoomModel>>()
    val filmVarMi = MutableLiveData<RoomModel>()
    val filmSil = MutableLiveData<Boolean>()
    val filmAraList = MutableLiveData<List<Movie>>()


    var myJob : Job? = null
    private val apiKey = "YOUR_KEY"
    private val dil = "tr-TR"

    fun populerFilimleriGetir(page: Int) {
        myJob= CoroutineScope(Dispatchers.IO).launch {
            yuklendiMi.postValue(true)
            try {
                val response = FilmClient.api.populerFilimleriGetir(apiKey, dil, page)
                if (response.isSuccessful) {
                    val populerFilm = response.body()?.results ?: emptyList()

                    withContext(Dispatchers.Main) {
                        mutableLiveData.value = populerFilm
                        yuklendiMi.value = false
                    }
                } else {
                    Log.e("Kategori", "Yanıt başarsız oldu : ${response.message()}")
                    withContext(Dispatchers.Main) {
                        yuklendiMi.value = false
                    }
                }
            } catch (e: Exception) {
                Log.e("PopulerFragment", "Error fetching popular movies", e)
                withContext(Dispatchers.Main) {
                    yuklendiMi.value = false
                }
            }
        }
    }

    fun kategorileriGetir() {
        myJob = CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = FilmClient.api.kategoriyeGoreGetir(apiKey, dil)
                if (response.isSuccessful) {
                    val kategori = response.body()?.genres ?: emptyList()

                    withContext(Dispatchers.Main) {
                        kategoriler.value = kategori
                    }
                } else {
                    Log.e("Kategori", "Yanıt başarsız oldu : ${response.message()}")
                }
            } catch (e: Exception) {
                Log.e("Kategori", "Veriler alınamadı: ${e.message}")
            }
        }
    }

    fun kategoriFilmGetir(id : String) {
        myJob = CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = FilmClient.api.filmleriTuruneGoreAl(apiKey, id)
                if (response.isSuccessful) {
                    val kategori =  response.body()?.results?: emptyList()

                    withContext(Dispatchers.Main) {
                        mutableLiveData.value = kategori
                    }
                } else {
                    Log.e("Kategori", "Yanıt başarsız oldu : ${response.message()}")
                }
            } catch (e: Exception) {
                Log.e("Kategori", "Veriler alınamadı: ${e.message}")
            }
        }
    }

    fun trendFilimler(){
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val yanit = FilmClient.api.haftaninTrendleriGetir(apiKey)
                if (yanit.isSuccessful){
                    val trendFilmListesi = yanit.body()?.results?: emptyList()
                    // IU da gözlemele
                    withContext(Dispatchers.Main){
                        mutableLiveData.value = trendFilmListesi
                    }
                }else{
                    Log.e("trend", "Yanıt başarsız oldu : ${yanit.message()}")
                }

            }catch (e:Exception){
                Log.e("trend", "Hatalı Deneme : ${e.message}")
            }
        }
    }


    fun secilenKategoriFilmleriGetir(kategoriAdi: String) {
         myJob = CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = FilmClient.api.kategoriyeGoreGetir(apiKey, dil)
                if (response.isSuccessful) {
                    val kategoriListesi = response.body()?.genres ?: emptyList()
                    val kategori = kategoriListesi.find { it.name == kategoriAdi }

                    kategori?.let {
                        val filmResponse = FilmClient.api.filmleriTuruneGoreAl(apiKey, it.id.toString())
                        if (filmResponse.isSuccessful) {
                            val secilenFilmler = filmResponse.body()?.results ?: emptyList()

                            withContext(Dispatchers.Main) {
                                secileniFilmGetir.value = secilenFilmler
                            }
                        } else {
                            Log.e("FilmlerRepo", "Filmleri Getirme Başarısız: ${filmResponse.message()}")
                        }
                    }
                } else {
                    Log.e("FilmlerRepo", "Kategorileri Getirme Başarısız: ${response.message()}")
                }
            } catch (e: Exception) {
                Log.e("FilmlerRepo", "Hata: ${e.message}")
            }
        }
    }

    fun filmEkle(roomModel: RoomModel) {
        CoroutineScope(Dispatchers.IO).launch {
            fDao.filmEkle(roomModel) // Ekleme işlemini veritabanına yap
            withContext(Dispatchers.Main) {
                Log.e("eklenen film", "baslik :${roomModel.title}, içerik : ${roomModel.overview} ")
                filmEklendi.value = roomModel
            }
        }
    }

    fun eklenenFilmleriGetir(){
        try {
            CoroutineScope(Dispatchers.IO).launch {
                val filmListesi =  fDao.filimleriGetir()
                if (filmListesi.isEmpty()){
                    withContext(Dispatchers.Main){
                        filmleriGetir.value = filmListesi
                    }
                }else{
                    withContext(Dispatchers.Main){
                        filmleriGetir.value = filmListesi
                    }
                    filmListesi.forEach{
                        Log.e("roomFilmler ","baslik :${it.title}, içerik : ${it.overview} id : ${it.id} ")
                    }
                }

            }
        }catch (e: Exception){
            Log.e("hata","liste : ${e.message}")
        }

    }

    fun filmVarMi(ad : String){
        try {
            CoroutineScope(Dispatchers.IO).launch {
                val film = fDao.filmVarMi(ad)
                withContext(Dispatchers.Main){
                    filmVarMi.value = film
                }
            }

        }catch (e : Exception){
            Log.e("hata",": ${e.message}")

        }


    }


    fun filmSil(filmAd: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val rowsDeleted = fDao.filmSil(filmAd)
                withContext(Dispatchers.Main) {
                    filmSil.value = rowsDeleted >= 0
                    if (rowsDeleted >= 0) {
                        Log.d("FilmlerRepo", "Film başarıyla silindi: $filmAd")
                    } else {
                        Log.d("FilmlerRepo", "Silinecek film bulunamadı: $filmAd")
                    }
                }
            } catch (e: Exception) {
                Log.e("FilmlerRepo", "Film silme hatası: ${e.message}")
                withContext(Dispatchers.Main) {
                    filmSil.value = false
                }
            }
        }
    }

    fun filmAra(filmAd: String) {
        try {
            CoroutineScope(Dispatchers.IO).launch {
                val response = FilmClient.api.filmAra(apiKey, filmAd)
                if (response.isSuccessful) {
                    val filmList = response.body()?.results ?: emptyList()
                    withContext(Dispatchers.Main) {
                        if (filmList.isEmpty()) {
                            Log.e("arananFilm", "Aradığınız film bulunamadı, sonuç sayısı: ${filmList.size}")
                        } else {
                            filmAraList.value = filmList
                        }
                    }
                } else {
                    Log.e("arananFilm", "Veri çekerken hata oluştu: ${response.code()} ${response.message()}")
                }
            }
        } catch (e: Exception) {
            Log.e("arananFilm", "Beklenmedik bir hata oluştu: ${e.message}")
        }
    }


}
