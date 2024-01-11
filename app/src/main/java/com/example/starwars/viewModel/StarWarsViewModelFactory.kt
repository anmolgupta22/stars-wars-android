package com.example.starwars.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.starwars.api.StarWarsRepository
import javax.inject.Inject

class StarWarsViewModelFactory @Inject constructor(private val instance: StarWarsRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(StarWarsRepository::class.java).newInstance(instance)
    }
}