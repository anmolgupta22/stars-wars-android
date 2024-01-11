package com.example.starwars

import android.app.Application
import com.example.starwars.api.AppComponent
import com.example.starwars.api.DaggerAppComponent

class MyApplication : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder().build()
    }

}