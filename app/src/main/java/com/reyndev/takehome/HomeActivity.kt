package com.reyndev.takehome

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

        adapter = TakeHomeRecyclerAdapter()

        binding.recyclerView.adapter = adapter

        viewModel.character.observe(this) {
            Log.v(TAG, "Character name ${it.name}")
        }

        viewModel.characters.observe(this) {
            adapter.submitList(it)
        }
    }
}