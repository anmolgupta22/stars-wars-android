package com.example.starwars.api

import com.example.starwars.fragment.StarCharactersFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApiModule::class, RepositoryModule::class])
interface AppComponent {
    fun inject(fragment: StarCharactersFragment)
}