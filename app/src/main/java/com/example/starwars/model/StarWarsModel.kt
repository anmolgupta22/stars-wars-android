package com.example.starwars.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "tbl_star_wars")
data class StarWarsResponse(

    @ColumnInfo
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,

    @ColumnInfo
    var count: Int? = null,

    @ColumnInfo
    var next: String? = null,

    @ColumnInfo
    var previous: String? = null,

    @ColumnInfo
    var results: ArrayList<Results> = arrayListOf()

)

@Parcelize
data class Results(

    var name: String? = null,
    var height: String? = null,
    var mass: String? = null,
    var hair_color: String? = null,
    var skin_color: String? = null,
    var eye_color: String? = null,
    var birth_year: String? = null,
    var gender: String? = null,
    var films: ArrayList<String> = arrayListOf(),
    var created: String? = null,
    var edited: String? = null,
    var url: String? = null

) : Parcelable
