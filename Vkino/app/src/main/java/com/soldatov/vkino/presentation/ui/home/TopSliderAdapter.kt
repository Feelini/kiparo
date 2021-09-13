package com.soldatov.vkino.presentation.ui.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.soldatov.domain.models.DomainTopSliderInfo
import com.soldatov.vkino.R
import com.soldatov.vkino.databinding.ItemTopSliderBinding
import com.squareup.picasso.Picasso

class TopSliderAdapter() : RecyclerView.Adapter<TopSliderAdapter.TopSliderViewHolder>() {

    private var filmsList: List<DomainTopSliderInfo> = ArrayList()
    private lateinit var navController: NavController

    @SuppressLint("NotifyDataSetChanged")
    fun setTopSliderInfo(topSliderList: List<DomainTopSliderInfo>, gettingNavController: NavController){
        filmsList = topSliderList
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

        fun bindData(topSliderInfo: DomainTopSliderInfo, navController: NavController){
            binding.name.text = topSliderInfo.title
            binding.year.text = topSliderInfo.year.toString()
            binding.genres.text = genresToString(topSliderInfo.genres)
            Picasso.with(itemView.context).load(topSliderInfo.poster).into(binding.poster)
            itemView.setOnClickListener{
                navController.navigate(R.id.action_homeFragment_to_filmFragment)
            }
        }

        private fun genresToString(genres: List<String>): String{
            var result = ""
            genres.forEach{
                result += it
                result += ", "
            }
            return if (result.length> 2) {
                result.substring(0, result.length - 2)
            } else {
                ""
            }
        }
    }
}