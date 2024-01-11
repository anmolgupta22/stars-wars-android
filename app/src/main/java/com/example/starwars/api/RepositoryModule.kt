package com.example.starwars.api

import dagger.Module
import dagger.Provides


@Module
class RepositoryModule {
    @Provides
    fun provideStarWarsRepository(apiService: ApiService): StarWarsRepository {
        return StarWarsRepositoryImpl(apiService)
    }


}
