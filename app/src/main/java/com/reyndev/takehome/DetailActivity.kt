package com.reyndev.takehome

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import coil.load
import coil.transform.CircleCropTransformation
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.reyndev.takehome.databinding.ActivityDetailBinding
import com.reyndev.takehome.model.Favorite
import com.reyndev.takehome.viewmodel.FavoriteViewModel
import com.reyndev.takehome.viewmodel.FavoriteViewModelFactory
import com.reyndev.takehome.viewmodel.RickMortyViewModel
import com.reyndev.takehome.viewmodel.RickMortyViewModelFactory

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding

    private val rmViewModel: RickMortyViewModel by viewModels { RickMortyViewModelFactory() }
    private val favViewModel: FavoriteViewModel by viewModels {
        FavoriteViewModelFactory(
            (application as TakeHomeApplication).database.rickMortyDao()
        )
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val charId = intent?.getIntExtra(INTENT_CHAR_ID, -1)

        if (charId == -1) {
            MaterialAlertDialogBuilder(this)
                .setTitle("Error")
                .setMessage("An error occured while trying to retrieve character detail")
                .setPositiveButton("Return") { _, _ -> finish() }
                .show()

            return
        }

        rmViewModel.getCharacterById(charId!!).let {
            rmViewModel.character.observe(this) { rm ->
                binding.image.load(rm.image) {
                    transformations(CircleCropTransformation())
                }

                binding.name.text = rm.name
                binding.gender.text = rm.gender
                binding.species.text = rm.species
                binding.origin.text = rm.origin.name
                binding.location.text = rm.location.name

                favViewModel.isFavorite(rm.id).observe(this) { isFav ->
                    when (isFav) {
                        true -> {
                            binding.btnFavorite.setOnClickListener {
                                favViewModel.removeFavorite(
                                    Favorite(
                                        rm.id,
                                        rm.name,
                                        rm.image
                                    )
                                )

                                Toast.makeText(
                                    this,
                                    "${rm.name} removed from favorite",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }

                            binding.btnFavorite.setImageDrawable(getDrawable(R.drawable.ic_star_filled))
                        }
                        false -> {
                            binding.btnFavorite.setOnClickListener {
                                favViewModel.addFavorite(
                                    Favorite(
                                        rm.id,
                                        rm.name,
                                        rm.image
                                    )
                                )

                                Toast.makeText(
                                    this,
                                    "${rm.name} added to favorite",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }

                            binding.btnFavorite.setImageDrawable(getDrawable(R.drawable.ic_star))
                        }
                    }
                }
            }
        }
    }

    companion object {
        const val INTENT_CHAR_ID = "INTENT_CHAR_ID"
    }
}