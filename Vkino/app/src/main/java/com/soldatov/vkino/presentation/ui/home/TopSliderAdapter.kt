package com.soldatov.vkino.presentation.ui.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.soldatov.domain.models.DomainFilmSliderInfo
import com.soldatov.vkino.R
import com.soldatov.vkino.databinding.ItemTopSliderBinding
import com.soldatov.vkino.presentation.ui.film.FILM_ID_KEY
import com.soldatov.vkino.presentation.utils.Helper
import com.squareup.picasso.Picasso

class TopSliderAdapter: RecyclerView.Adapter<TopSliderAdapter.TopSliderViewHolder>() {

    private var filmsList: List<DomainFilmSliderInfo> = ArrayList()
    private lateinit var navController: NavController

    @SuppressLint("NotifyDataSetChanged")
    fun setTopSliderInfo(filmSliderList: List<DomainFilmSliderInfo>, gettingNavController: NavController){
        filmsList = filmSliderList
        navController = gettingNavController
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopSliderViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemTopSliderBinding.inflate(inflater, parent, false)
        return TopSliderViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: TopSliderViewHolder, position: Int) {
        holder.bindData(filmsList[position], navController)
    }

    override fun getItemCount(): Int {
        return filmsList.size
    }

    class TopSliderViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        private val binding = ItemTopSliderBinding.bind(itemView)

        fun bindData(filmSliderInfo: DomainFilmSliderInfo, navController: NavController){
            binding.name.text = filmSliderInfo.title
            binding.year.text = filmSliderInfo.year.toString()
            binding.genres.text = Helper.listToString(filmSliderInfo.genres)
            Picasso.with(itemView.context).load(filmSliderInfo.poster).into(binding.poster)
            itemView.setOnClickListener{
                navController.navigate(R.id.action_homeFragment_to_filmFragment,
                bundleOf(FILM_ID_KEY to filmSliderInfo.filmId))
            }
        }
    }
}