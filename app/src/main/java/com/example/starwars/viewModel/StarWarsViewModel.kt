package com.example.starwars.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.starwars.api.StarWarsRepository
import com.example.starwars.database.RoomDatabase
import com.example.starwars.model.StarWarsResponse
import kotlinx.coroutines.launch
import javax.inject.Inject

class StarWarsViewModel @Inject constructor(private val repository: StarWarsRepository) :
    ViewModel() {
    private val _peoples = MutableLiveData<StarWarsResponse>()

    val peoples: LiveData<StarWarsResponse> get() = _peoples

    fun getPeoples(page: Int?) {
        viewModelScope.launch {
            try {
                Log.d("TAG", "getPeoples: chekc the page $page")
                _peoples.value = repository.getPeoples(page)
            } catch (e: Exception) {
                // Handle exception
            }
        }
    }

}
