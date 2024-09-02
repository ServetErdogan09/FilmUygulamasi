package com.example.filmuygulamasi.adapter

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.filmuygulamasi.R
import com.example.filmuygulamasi.databinding.AramaDuzenRecyclerBinding
import com.example.filmuygulamasi.retrofit.model.Movie
import com.example.filmuygulamasi.ui.fragment.AramaFragmentDirections
import com.example.filmuygulamasi.util.gecisYap
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso

class AraAdapter(val fragment: Fragment, private var filmList: List<Movie>) : RecyclerView.Adapter<AraAdapter.AraTutucu>() {

    class AraTutucu(var tasarim: AramaDuzenRecyclerBinding) : RecyclerView.ViewHolder(tasarim.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AraTutucu {
        val tasarim = AramaDuzenRecyclerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AraTutucu(tasarim)
    }

    override fun getItemCount(): Int {
        return filmList.size
    }

    override fun onBindViewHolder(holder: AraTutucu, position: Int) {

        val film = filmList[position]

        val baseUrl = "https://image.tmdb.org/t/p/w500"
        val posterUrl = baseUrl + film.poster_path

        Picasso.get()
            .load(posterUrl)
            .resize(200, 200)
            .centerCrop()
            .into(holder.tasarim.resimImage)

        holder.tasarim.noktaImage.setOnClickListener {
            showCustomAlertDialog(film)
        }
    }

    fun aramaGuncele(list: List<Movie>) {
        filmList = list
        notifyDataSetChanged()
    }



    private fun showCustomAlertDialog(film: Movie) {
        val dialogView =
            LayoutInflater.from(fragment.requireContext()).inflate(R.layout.alertdialog_duzen, null)

        val titleTextView = dialogView.findViewById<TextView>(R.id.dialog_title)
        val messageTextView = dialogView.findViewById<TextView>(R.id.dialog_message)
        val actionTextView = dialogView.findViewById<TextView>(R.id.dialog_action)
        val dialogIcon = dialogView.findViewById<ImageView>(R.id.dialog_icon)

        titleTextView.text = film.title ?: "Başlık Yok"
        messageTextView.text = film.overview ?: "Açıklama Yok"

        actionTextView.text = "Detaylara Git"
        actionTextView.setTextColor(Color.BLACK)
        actionTextView.textSize = 15f
        actionTextView.setBackgroundColor(Color.WHITE)
        dialogView.setBackgroundColor(Color.WHITE)
        actionTextView.setPadding(20, 10, 20, 10)
        actionTextView.gravity = Gravity.CENTER

        val dialog = Dialog(fragment.requireContext())
        dialog.setContentView(dialogView)

        actionTextView.setOnClickListener {
            dialog.dismiss()

            val gecis = AramaFragmentDirections.actionAramaFragmentToDetayFragment(film, "arama")
            fragment.findNavController().navigate(gecis)
        }

        val window = dialog.window
        if (window != null) {
            val params = window.attributes
            params.gravity = Gravity.BOTTOM
            params.width = WindowManager.LayoutParams.MATCH_PARENT
            window.attributes = params
            window.setBackgroundDrawableResource(android.R.color.transparent)
        }

        dialog.show()

    }
}
