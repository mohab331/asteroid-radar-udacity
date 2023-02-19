package com.udacity.asteroidradar.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.udacity.asteroidradar.data.api.ApiService
import com.udacity.asteroidradar.data.constants.Constants
import com.udacity.asteroidradar.data.database.AsteroidDatabase
import com.udacity.asteroidradar.data.database.PictureDatabase
import com.udacity.asteroidradar.repository.asteroidRepository.AsteroidRepository
import retrofit2.HttpException

class WeeklyWorker(appContext: Context, params: WorkerParameters) :
    CoroutineWorker(appContext, params) {


    override suspend fun doWork(): Result {
        val asteroidDatabase = AsteroidDatabase.getInstance( applicationContext)
        val pictureDatabase = PictureDatabase.getInstance( applicationContext)
        val apiService = ApiService
        val asteroidRepository = AsteroidRepository(asteroidDatabase.dao , pictureDatabase.dao , apiService.retrofitService)

        return try {
            asteroidRepository.refreshAsteroid()
            asteroidRepository.getPictureOfDay(apiKey =Constants.API_KEY )
            Result.success()
        } catch (exception: HttpException) {
            return Result.retry()
        }
    }

    companion object {
        const val WORK_NAME = "RefreshDataWorker"
    }
}