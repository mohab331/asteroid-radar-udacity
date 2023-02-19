package com.udacity.asteroidradar.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.udacity.asteroidradar.data.model.Asteroid

@Dao
interface AsteroidsDao {
    @Query("SELECT * FROM asteroids_table ORDER BY closeApproachDate ASC")
    fun getAllAsteroids(): List<Asteroid>

    @Query("SELECT * FROM asteroids_table WHERE closeApproachDate == :date")
    suspend fun getAsteroidsOfToday(
        date: String,
    ): List<Asteroid>

    @Query("SELECT * FROM asteroids_table WHERE closeApproachDate > :startDate AND closeApproachDate < :endDate ORDER BY closeApproachDate ASC")
    suspend fun getAsteroidsOfTheWeek(
        startDate: String,
        endDate: String
    ): List<Asteroid>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(asteroids: List<Asteroid>) : List<Long>

    @Query("DELETE FROM asteroids_table")
    suspend fun deleteAll() : Int
}