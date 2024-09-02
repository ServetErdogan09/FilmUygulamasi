package com.example.filmuygulamasi.retrofit

import com.example.filmuygulamasi.retrofit.model.FilmCevap
import com.example.filmuygulamasi.retrofit.model.KategoriModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface TMDBApi {

    @GET("movie/popular")
    suspend fun populerFilimleriGetir(
        @Query("api_key") apiKey: String,
        @Query("language") language: String = "tr-TR",
        @Query("page") page: Int
    ): Response<FilmCevap>

    @GET("genre/movie/list")
    suspend fun kategoriyeGoreGetir(
        @Query("api_key") apiKey: String,
        @Query("language") language: String = "tr-TR"
    ): Response<KategoriModel>

    @GET("discover/movie")
    suspend fun filmleriTuruneGoreAl(
        @Query("api_key") apiKey: String,
        @Query("with_genres") genreId: String
    ): Response<FilmCevap>

    @GET("trending/movie/week")
    suspend fun haftaninTrendleriGetir(
        @Query("api_key") apiKey: String,
        @Query("language") genreId: String = "tr-TR"
    ): Response<FilmCevap>

    @GET("search/movie")
   suspend fun filmAra(
        @Query("api_key") apiKey: String,
        @Query("query") query: String,
        @Query("language") language: String = "tr-TR",
        @Query("page") page: Int = 1
    ): Response<FilmCevap>
}
