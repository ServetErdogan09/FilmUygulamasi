package com.example.filmuygulamasi.ui.fragment

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.filmuygulamasi.R
import com.example.filmuygulamasi.adapter.AraAdapter
import com.example.filmuygulamasi.databinding.FragmentAramaBinding
import com.example.filmuygulamasi.ui.viewmodel.AramaViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AramaFragment : Fragment(){

    private lateinit var tasarim : FragmentAramaBinding
    private val aramaViewModel : AramaViewModel by viewModels()
    private lateinit var araAdapter: AraAdapter
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        tasarim = DataBindingUtil.inflate(inflater,R.layout.fragment_arama,container,false)

        tasarim.toolbar.title ="Arama"
        // manuel olarak bağlamamız gerkiyor toolbarı
        (requireActivity() as AppCompatActivity).setSupportActionBar(tasarim.toolbar)
        setHasOptionsMenu(true)
        tasarim.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        araAdapter = AraAdapter(this@AramaFragment, emptyList())
        tasarim.recyclerView.adapter = araAdapter

        liste()
        return tasarim.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.arama_menu,menu)
        val searchViewItem = menu.findItem(R.id.arama_iconu)
        val searchView = searchViewItem.actionView as SearchView


        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
               if (query.isNullOrBlank()){
                   araAdapter.aramaGuncele(emptyList())
               }else{
                   aramaViewModel.ara(query)
               }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText.isNullOrBlank()){
                    araAdapter.aramaGuncele(emptyList())
                }else{
                    aramaViewModel.ara(newText)
                }
               return true
            }

        })

    }
    private fun liste(){
        aramaViewModel.liste.observe(viewLifecycleOwner){ filmList->
            araAdapter.aramaGuncele(filmList)
        }
    }


}