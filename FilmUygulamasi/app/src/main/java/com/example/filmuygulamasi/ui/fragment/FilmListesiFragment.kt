package com.example.filmuygulamasi.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.filmuygulamasi.R
import com.example.filmuygulamasi.adapter.FilmListesiAdapter
import com.example.filmuygulamasi.databinding.FragmentFilmListesiFragmentBinding
import com.example.filmuygulamasi.room.RoomModel
import com.example.filmuygulamasi.ui.viewmodel.FilmListesiViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FilmListesiFragment : Fragment() {
    private lateinit var tasarim : FragmentFilmListesiFragmentBinding
    private val filmListesiViewModel : FilmListesiViewModel by viewModels()
    private lateinit var filmListesiAdapter: FilmListesiAdapter
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        tasarim = DataBindingUtil.inflate(inflater,R.layout.fragment_film_listesi_fragment,container,false)

        tasarim.recyclerView.layoutManager = GridLayoutManager(requireContext(),2)

        filmListesiViewModel.filmListe.observe(viewLifecycleOwner){filmListe->
                filmListesiAdapter = FilmListesiAdapter(requireContext(),filmListe)
                tasarim.recyclerView.adapter = filmListesiAdapter
        }
        filmListesiViewModel.filmGetir()

        return tasarim.root
    }



}