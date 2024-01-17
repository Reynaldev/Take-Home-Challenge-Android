package com.reyndev.takehome

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.reyndev.takehome.adapter.TakeHomeRecyclerAdapter
import com.reyndev.takehome.databinding.ActivitySearchBinding
import com.reyndev.takehome.viewmodel.RickMortyViewModel
import com.reyndev.takehome.viewmodel.RickMortyViewModelFactory

class SearchActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySearchBinding

    private val viewModel: RickMortyViewModel by viewModels { RickMortyViewModelFactory() }

    private lateinit var adapter: TakeHomeRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.searchInput.editText?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                viewModel.getCharactersByName(binding.searchInput.editText?.text.toString())
            }
        })

        adapter = TakeHomeRecyclerAdapter {
            val detailIntent = Intent(this, DetailActivity::class.java)
            detailIntent.putExtra(DetailActivity.INTENT_CHAR_ID, it.id)
            startActivity(detailIntent)
        }
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = GridLayoutManager(
            this, 3, RecyclerView.VERTICAL, false)

        viewModel.characters.observe(this) {
            adapter.submitList(it)
        }
    }
}