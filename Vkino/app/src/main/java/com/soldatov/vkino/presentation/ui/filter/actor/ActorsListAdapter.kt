package com.soldatov.vkino.presentation.ui.filter.actor

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.soldatov.domain.models.Actor
import com.soldatov.vkino.databinding.ItemFilterListBinding
import com.soldatov.vkino.databinding.ItemProgressBarBinding

class ActorsListAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var actorsList: List<Actor> = listOf()
    private var chosenActors: ArrayList<Actor> = arrayListOf()
    private val VIEW_ITEM = 1
    private val VIEW_PROG = 0
    private var isProgress = true

    @SuppressLint("NotifyDataSetChanged")
    fun setSearchActors(actorList: List<Actor>, newChosenActors: List<Actor>){
        actorsList = actorList
        chosenActors = newChosenActors as ArrayList<Actor>
        notifyDataSetChanged()
    }

    fun addProgress(){
        isProgress = true
    }

    fun removeProgress(){
        isProgress = false
    }

    fun getChosenActors(): List<Actor>{
        return chosenActors.toList()
    }

    private val addChosenActor: (Actor) -> Unit = {
        chosenActors.add(it)
    }

    private val removeChosenActor: (Actor) -> Unit = {
        chosenActors.remove(it)
    }

    private val isCheckedActor: (Actor) -> Boolean = {
        chosenActors.contains(it)
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == actorsList.size) VIEW_PROG else VIEW_ITEM
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val viewHolder: RecyclerView.ViewHolder
        val inflater = LayoutInflater.from(parent.context)
        viewHolder = if (viewType == VIEW_ITEM) {
            val binding = ItemFilterListBinding.inflate(inflater, parent, false)
            SearchActorsViewHolder(binding.root)
        } else {
            val binding = ItemProgressBarBinding.inflate(inflater, parent, false)
            ProgressBarViewHolder(binding.root)
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is SearchActorsViewHolder) {
            holder.bindData(
                actorsList[position],
                isCheckedActor,
                addChosenActor,
                removeChosenActor
            )
        } else {
            (holder as ProgressBarViewHolder).bindData()
        }
    }

    override fun getItemCount(): Int {
        return if (isProgress){
            if (actorsList.isEmpty()) 0 else actorsList.size + 1
        } else {
            actorsList.size
        }
    }

    class SearchActorsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        private val binding = ItemFilterListBinding.bind(itemView)

        fun bindData(
            actor: Actor,
            isChecked: (Actor) -> Boolean,
            addActor: (Actor) -> Unit,
            removeActor: (Actor) -> Unit
        ) {
            binding.filterTitle.text = actor.name
            binding.checkbox.isChecked = isChecked(actor)
            itemView.setOnClickListener {
                if (isChecked(actor)) {
                    removeActor(actor)
                    binding.checkbox.isChecked = false
                } else {
                    addActor(actor)
                    binding.checkbox.isChecked = true
                }
            }
        }
    }

    class ProgressBarViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val binding = ItemProgressBarBinding.bind(itemView)

        fun bindData() {
            binding.progress.isIndeterminate = true
        }
    }
}