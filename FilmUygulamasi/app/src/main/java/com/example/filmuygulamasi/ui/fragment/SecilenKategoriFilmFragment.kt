package com.example.filmuygulamasi.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.filmuygulamasi.R
import com.example.filmuygulamasi.adapter.SecilenFilmKategoriAdapter
import com.example.filmuygulamasi.databinding.FragmentSecilenKategoriFilmBinding
import com.example.filmuygulamasi.ui.viewmodel.SecilenKategoriViewModel
import com.example.filmuygulamasi.util.gecisYap
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class  SecilenKategoriFilmFragment : Fragment() {
    private lateinit var tasarim: FragmentSecilenKategoriFilmBinding
    private var  kategori : String? = null
    private lateinit var secilenFilmKategoriAdapter: SecilenFilmKategoriAdapter
    private val secilenKategoriViewModel : SecilenKategoriViewModel by viewModels()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        tasarim = DataBindingUtil.inflate(inflater,R.layout.fragment_secilen_kategori_film,container,false)

        val bottomNav = requireActivity().findViewById<BottomNavigationView>(R.id.bottomNav)
        bottomNav.visibility = View.GONE


        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().navigate(R.id.action_secilenKategoriFilmFragment_to_anaSayfaFragment2)
                bottomNav.visibility = View.VISIBLE
            }

        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner,callback)

         arguments?.let {
            kategori = it.getString("kategori")
             Log.e("katgeori","gelen : $kategori")
        }

        tasarim.toolbarBaslik = "$kategori"

        tasarim.toolbarKategori.setNavigationOnClickListener {
            val gecis = SecilenKategoriFilmFragmentDirections.actionSecilenKategoriFilmFragmentToAnaSayfaFragment2()
            Navigation.gecisYap(it,gecis)
            bottomNav.visibility = View.VISIBLE
        }


        kategori?.let { secilenKategoriViewModel.filmKategorisiGetir(it) }
        secilenKategoriViewModel.secilenFilm.observe(viewLifecycleOwner){secilenListe->
           secilenFilmKategoriAdapter = SecilenFilmKategoriAdapter(requireContext(),secilenListe)
           tasarim.adapterNesnesi = secilenFilmKategoriAdapter
        }

        return tasarim.root

    }

}