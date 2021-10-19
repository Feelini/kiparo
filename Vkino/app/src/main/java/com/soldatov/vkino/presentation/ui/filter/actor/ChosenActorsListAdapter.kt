package com.soldatov.vkino.presentation.ui.filter.actor

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.soldatov.domain.models.Actor
import com.soldatov.vkino.databinding.ItemChosenListBinding

class ChosenActorsListAdapter: RecyclerView.Adapter<ChosenActorsListAdapter.ChosenActorsListViewHolder>() {

    private var actorsList: List<Actor> = listOf()
    private lateinit var onRemoveChosenActorListener: OnRemoveChosenActorListener

    interface OnRemoveChosenActorListener{
        fun onRemoveChosenActor(actor: Actor)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setChosenActors(
        newActors: List<Actor>,
        onRemoveChosenActor: OnRemoveChosenActorListener
    ){
        actorsList = newActors
        onRemoveChosenActorListener = onRemoveChosenActor
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChosenActorsListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemChosenListBinding.inflate(inflater, parent, false)
        return ChosenActorsListViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: ChosenActorsListViewHolder, position: Int) {
        holder.bindData(actorsList[position], onRemoveChosenActorListener)
    }

    override fun getItemCount(): Int {
        return actorsList.size
    }

    class ChosenActorsListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        private val binding = ItemChosenListBinding.bind(itemView)

        fun bindData(actor: Actor, onRemoveChosenActorListener: OnRemoveChosenActorListener){
            binding.name.text = actor.name
            binding.cancel.setOnClickListener {
                onRemoveChosenActorListener.onRemoveChosenActor(actor)
            }
        }
    }
}