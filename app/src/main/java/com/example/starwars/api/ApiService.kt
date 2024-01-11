package com.example.starwars.api

import com.example.starwars.model.StarWarsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("people/")
    suspend fun getStarWarsPeople(@Query("page") page : Int?): Response<StarWarsResponse>
}