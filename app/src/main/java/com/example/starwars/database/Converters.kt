package com.example.starwars.database

import androidx.room.TypeConverter
import com.example.starwars.model.Results
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class Converters {

    @TypeConverter
    fun toResultsList(list: String?): ArrayList<Results?>? {
        val typeToken: Type = object : TypeToken<ArrayList<Results?>?>() {}.type
        return Gson().fromJson<ArrayList<Results?>>(list, typeToken)
    }

    @TypeConverter
    fun fromResultsList(list: ArrayList<Results?>?): String? {
        return Gson().toJson(list)
    }
}