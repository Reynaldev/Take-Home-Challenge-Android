package com.reyndev.takehome

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import coil.load
import coil.transform.CircleCropTransformation
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.reyndev.takehome.databinding.ActivityDetailBinding
import com.reyndev.takehome.viewmodel.RickMortyViewModel
import com.reyndev.takehome.viewmodel.RickMortyViewModelFactory

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding

    private val viewModel: RickMortyViewModel by viewModels { RickMortyViewModelFactory() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val charId = intent?.getIntExtra(INTENT_CHAR_ID, -1)

        if (charId != -1) {
            viewModel.getCharacterById(charId!!).let {
                viewModel.character.observe(this) {
                    binding.image.load(it.image) {
                        transformations(CircleCropTransformation())
                    }

                    binding.name.text = it.name
                    binding.gender.text = it.gender
                    binding.species.text = it.species
                    binding.origin.text = it.origin.name
                    binding.location.text = it.location.name
                }
            }
        } else {
            MaterialAlertDialogBuilder(this)
                .setTitle("Error")
                .setMessage("An error occured while trying to retrieve character detail")
                .setPositiveButton("Return") { _, _ -> finish() }
                .show()
        }
    }

    companion object {
        const val INTENT_CHAR_ID = "INTENT_CHAR_ID"
    }
}