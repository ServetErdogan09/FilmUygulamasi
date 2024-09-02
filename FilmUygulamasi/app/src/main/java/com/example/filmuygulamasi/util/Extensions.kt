package com.example.filmuygulamasi.util

import android.view.View
import androidx.navigation.NavDirections
import androidx.navigation.Navigation

fun Navigation.gecisYap(it : View, id : NavDirections){
    findNavController(it).navigate(id)



}