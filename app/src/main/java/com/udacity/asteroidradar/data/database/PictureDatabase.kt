package com.udacity.asteroidradar.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.udacity.asteroidradar.data.model.Asteroid
import com.udacity.asteroidradar.data.model.PictureOfDay

@Database(entities = [PictureOfDay::class], version = 1, exportSchema = false)
abstract class PictureDatabase : RoomDatabase(){
        abstract val dao : PicturesDao

        companion object {
            @Volatile
            private var INSTANCE: PictureDatabase? = null

            fun getInstance(context: Context): PictureDatabase {
                synchronized(this) {
                    var instance = INSTANCE

                    if (instance == null) {
                        instance = Room.databaseBuilder(
                            context.applicationContext,
                            PictureDatabase::class.java,
                            "pictures_database"
                        )
                            .fallbackToDestructiveMigration()
                            .build()
                        INSTANCE = instance
                    }
                    return instance
                }
            }

        }

    }