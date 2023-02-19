package com.udacity.asteroidradar.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.udacity.asteroidradar.data.model.PictureOfDay

@Dao
interface PicturesDao {
    @Query("SELECT * FROM picture_table")
    suspend fun getPicture(): PictureOfDay

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPicture(pictureEntity: PictureOfDay) : Long

    @Query("DELETE FROM picture_table")
    suspend fun deleteAll() : Int
}