package com.example.filmuygulamasi.di

import android.content.Context
import androidx.room.Room
import com.example.filmuygulamasi.repo.FilmlerRespository
import com.example.filmuygulamasi.room.FilmlerDao
import com.example.filmuygulamasi.room.Veritabani
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    // bu sınıfı isteyen butun hiltmodelere FilmlerRespository nesnesi gönderilecek
    @Provides
    @Singleton
    fun provideFilmlerRespository(fDao : FilmlerDao) : FilmlerRespository{
        return FilmlerRespository(fDao)
    }

    // Contexti kendisi ototmatik sağlıyacak dıardan vermeyeceğiz
    @Provides
    @Singleton
    fun provideFilmlerDao(@ApplicationContext context: Context) : FilmlerDao{
         val vt = Room.databaseBuilder(context,Veritabani::class.java,"filim.db").build()
        return vt.getFilimler()
    }
}