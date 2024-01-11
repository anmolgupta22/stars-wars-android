package com.example.starwars.database

import android.content.Context
import androidx.room.*
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.starwars.database.dao.CharacterDao
import com.example.starwars.model.StarWarsResponse
import javax.inject.Inject


@Database(
    entities = [StarWarsResponse::class],
    version = 1,
    exportSchema = false
)

@TypeConverters(Converters::class)
abstract class DBHelper: RoomDatabase() {

    abstract fun characterDao(): CharacterDao

    companion object {

        private var appDataBaseInstance: DBHelper? = null

        @Synchronized
        fun getInstance(context: Context): DBHelper {
            if (appDataBaseInstance == null) {
                appDataBaseInstance = Room.databaseBuilder(
                    context.applicationContext,
                    DBHelper::class.java,
                    "star_war_database"
                )
                    .allowMainThreadQueries()
                    .addMigrations(MIGRATION_1_2)
                    .build()
            }
            return appDataBaseInstance!!
        }

        private val MIGRATION_1_2: Migration = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("CREATE TABLE tbl_star_wars (`id` INT PRIMARY KEY, `count` INT, `next` TEXT, `previous` TEXT, `location_data` TEXT)")
                db.execSQL("INSERT INTO tbl_star+wars (id, count, next, previous, `location_data`)")
            }
        }
    }
}