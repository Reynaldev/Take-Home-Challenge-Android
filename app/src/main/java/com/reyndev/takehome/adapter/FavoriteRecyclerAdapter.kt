package com.reyndev.takehome.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.reyndev.takehome.databinding.CharacterListItemBinding
import com.reyndev.takehome.model.Favorite

class FavoriteRecyclerAdapter(private val onClick: (Favorite) -> Unit)
    : ListAdapter<Favorite, FavoriteRecyclerAdapter.FavoriteViewHolder>(DiffCallback) {

    class FavoriteViewHolder(private val binding: CharacterListItemBinding)
        : RecyclerView.ViewHolder(binding.root) {

        fun bind(rickMorty: Favorite) {
            binding.name.text = rickMorty.name
            binding.image.load(rickMorty.image)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        return FavoriteViewHolder(CharacterListItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val item = getItem(position)

        holder.itemView.setOnClickListener { onClick(item) }

        holder.bind(item)
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Favorite>() {
            override fun areItemsTheSame(oldItem: Favorite, newItem: Favorite): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Favorite, newItem: Favorite): Boolean {
                return oldItem == newItem
            }
        }
    }
}