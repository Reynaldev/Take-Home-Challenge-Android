package com.reyndev.takehome

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.reyndev.takehome.adapter.TakeHomeRecyclerAdapter
import com.reyndev.takehome.databinding.ActivityMainBinding
import com.reyndev.takehome.viewmodel.RickMortyViewModel
import com.reyndev.takehome.viewmodel.RickMortyViewModelFactory

private const val TAG = "HomeActivity"

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: RickMortyViewModel by viewModels { RickMortyViewModelFactory() }

    private lateinit var adapter: TakeHomeRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = TakeHomeRecyclerAdapter {
            val detailIntent = Intent(this, DetailActivity::class.java)
            detailIntent.putExtra(DetailActivity.INTENT_CHAR_ID, it.id)
            startActivity(detailIntent)
        }

        binding.recyclerView.adapter = adapter

        binding.btnSearch.setOnClickListener {
            val searchIntent = Intent(this, SearchActivity::class.java)
            startActivity(searchIntent)
        }

        viewModel.characters.observe(this) {
            adapter.submitList(it)
        }
    }
}