package com.soldatov.vkino.presentation.ui.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.soldatov.domain.models.FilmSliderInfo
import com.soldatov.vkino.databinding.ItemHomePageFilmBinding
import com.soldatov.vkino.presentation.utils.getFilmTitle
import com.soldatov.vkino.presentation.utils.listToString
import com.squareup.picasso.Picasso

class HomePageFilmsAdapter: RecyclerView.Adapter<HomePageFilmsAdapter.HomePageFilmsViewHolder>() {

    private var filmsList: List<FilmSliderInfo> = ArrayList()
    private lateinit var navController: NavController

    @SuppressLint("NotifyDataSetChanged")
    fun setHomePageFilms(filmList: List<FilmSliderInfo>, gettingNavController: NavController){
        filmsList = filmList
        navController = gettingNavController
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomePageFilmsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemHomePageFilmBinding.inflate(inflater, parent, false)
        return HomePageFilmsViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: HomePageFilmsViewHolder, position: Int) {
        holder.bindData(filmsList[position], navController)
    }

    override fun getItemCount(): Int {
        return filmsList.size
    }

    class HomePageFilmsViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        private val binding = ItemHomePageFilmBinding.bind(itemView)

        fun bindData(filmSliderInfo: FilmSliderInfo, navController: NavController){
            binding.filmTitle.text = getFilmTitle(filmSliderInfo)
            binding.filmRating.text = filmSliderInfo.rating.toString()
            binding.filmCategoryValue.text = filmSliderInfo.category
            binding.filmGenreValue.text = listToString(filmSliderInfo.genres)
            binding.filmDescription.text = filmSliderInfo.description
            Picasso.with(itemView.context).load(filmSliderInfo.poster).into(binding.filmPoster)
//            itemView.setOnClickListener{
//                navController.navigate(R.id.action_homeFragment_to_filmFragment,
//                bundleOf(FILM_ID_KEY to filmSliderInfo.filmId))
//            }
        }
    }
}