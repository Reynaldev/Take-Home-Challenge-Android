package com.reyndev.takehome

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.reyndev.takehome.adapter.FavoriteRecyclerAdapter
import com.reyndev.takehome.databinding.ActivityMainBinding
import com.reyndev.takehome.viewmodel.FavoriteViewModel
import com.reyndev.takehome.viewmodel.FavoriteViewModelFactory

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val viewModel: FavoriteViewModel by viewModels {
        FavoriteViewModelFactory(
            (application as TakeHomeApplication).database.rickMortyDao()
        )
    }

    private lateinit var adapter: FavoriteRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = FavoriteRecyclerAdapter {
            val detailIntent = Intent(this, DetailActivity::class.java)
            detailIntent.putExtra(DetailActivity.INTENT_CHAR_ID, it.id)
            startActivity(detailIntent)
        }

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = GridLayoutManager(
            this, 3, RecyclerView.VERTICAL, false)

        binding.btnSearch.setOnClickListener {
            val searchIntent = Intent(this, SearchActivity::class.java)
            startActivity(searchIntent)
        }

        viewModel.favorites.observe(this) { faves ->
            adapter.submitList(faves)

            if (faves.isNotEmpty()) {
                binding.recyclerView.visibility = View.VISIBLE
                binding.status.visibility = View.GONE
            } else {
                binding.recyclerView.visibility = View.GONE
                binding.status.visibility = View.VISIBLE
            }
        }
    }
}