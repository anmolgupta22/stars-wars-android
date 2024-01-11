package com.example.starwars.api

import com.example.starwars.model.StarWarsResponse

interface StarWarsRepository {
    suspend fun getPeoples(page: Int?): StarWarsResponse?
}


