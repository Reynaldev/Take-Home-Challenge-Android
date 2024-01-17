package com.reyndev.takehome.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.reyndev.takehome.databinding.CharacterListItemBinding
import com.reyndev.takehome.model.RickMorty

class TakeHomeRecyclerAdapter(private val onClick: (RickMorty) -> Unit)
    : ListAdapter<RickMorty, TakeHomeRecyclerAdapter.TakeHomeViewHolder>(DiffCallback) {

    class TakeHomeViewHolder(private val binding: CharacterListItemBinding)
        : RecyclerView.ViewHolder(binding.root) {

        fun bind(rickMorty: RickMorty) {
            binding.name.text = rickMorty.name
            binding.image.load(rickMorty.image)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TakeHomeViewHolder {
        return TakeHomeViewHolder(CharacterListItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: TakeHomeViewHolder, position: Int) {
        val item = getItem(position)

        holder.itemView.setOnClickListener { onClick(item) }

        holder.bind(item)
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<RickMorty>() {
            override fun areItemsTheSame(oldItem: RickMorty, newItem: RickMorty): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: RickMorty, newItem: RickMorty): Boolean {
                return oldItem == newItem
            }
        }
    }
}