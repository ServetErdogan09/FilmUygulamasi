package com.example.filmuygulamasi.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.filmuygulamasi.databinding.FragmentPopulerBinding
import com.example.filmuygulamasi.adapter.PopulerAdapter
import com.example.filmuygulamasi.ui.viewmodel.PopulerViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PopulerFragment : Fragment() {

    private lateinit var tasarim: FragmentPopulerBinding
    private lateinit var populerAdapter: PopulerAdapter
    private val populerViewModel: PopulerViewModel by viewModels()

    private var currentPage = 1 // Başlangıç sayfası

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        tasarim = FragmentPopulerBinding.inflate(inflater, container, false)
        return tasarim.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        populerAdapter = PopulerAdapter(requireContext(), mutableListOf())
        tasarim.populerAdapter = populerAdapter

        // LiveData'yı gözlemle
        populerViewModel.populerFilmler.observe(viewLifecycleOwner, Observer { movies ->
            populerAdapter.addMovies(movies)
        })

        populerViewModel.yuklendiMi.observe(viewLifecycleOwner, Observer { loading ->
            // Yükleme göstergesini kontrol etmek için kullan
        })


        // Sonsuz kaydırma işlevini ekle
        tasarim.populartiRecycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val totalItemCount = layoutManager.itemCount
                val lastVisibleItem = layoutManager.findLastVisibleItemPosition()

                // Null kontrolü ekleyin
                val isLoaded = populerViewModel.yuklendiMi.value ?: true

                if (!isLoaded && totalItemCount <= (lastVisibleItem + 3)) {
                    currentPage++
                    populerViewModel.populerfilmGetir(page = currentPage)
                }
            }
        })

        // İlk sayfayı yükle
        populerViewModel.populerfilmGetir(page = currentPage)
    }
}
