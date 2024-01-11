package com.example.starwars.database


import android.content.Context
import com.example.starwars.api.StarWarsRepository
import com.example.starwars.database.dao.CharacterDao
import com.example.starwars.model.StarWarsResponse
import javax.inject.Inject


open class RoomDatabase @Inject constructor(
    private val characterDao: CharacterDao,
) {

    fun insertStarWarsList(context: Context, starWarsResponse: StarWarsResponse) {
        DBHelper.getInstance(context).characterDao().insert(starWarsResponse)
    }

//    suspend fun fetchAllLocationTracking(): StarWarsResponse {
//        return characterDao.fetchAllLocationTracking()
//    }
}