package com.example.starwars.api

import com.example.starwars.model.StarWarsResponse
import java.io.IOException
import javax.inject.Inject

class StarWarsRepositoryImpl @Inject constructor(private val apiService: ApiService) :
    StarWarsRepository {

    override suspend fun getPeoples(page: Int?): StarWarsResponse? {
        val response = apiService.getStarWarsPeople(page)
        if (response.isSuccessful && response.body() != null) {
            return response.body()
        } else {
            // Handle error
            throw IOException("Failed to fetch characters")
        }
    }
}
