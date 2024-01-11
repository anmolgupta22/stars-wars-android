package com.example.starwars.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.starwars.model.StarWarsResponse


@Dao
interface CharacterDao: BaseDao<StarWarsResponse> {

    @Query("Select * from tbl_star_wars")
    suspend fun fetchStarWars(): StarWarsResponse?
}