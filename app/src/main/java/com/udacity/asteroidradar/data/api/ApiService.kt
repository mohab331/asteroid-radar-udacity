package com.udacity.asteroidradar.data.api

import com.udacity.asteroidradar.data.constants.Constants
import com.udacity.asteroidradar.data.model.Asteroid
import com.udacity.asteroidradar.data.model.PictureOfDay
import kotlinx.coroutines.Deferred
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("neo/rest/v1/feed")
    suspend  fun getAsteroids(
        @Query("start_date") startDate: String,
        @Query("end_date") endDate: String,
        @Query("api_key") apiKey: String = Constants.API_KEY
    ): ResponseBody

    @GET("planetary/apod")
    suspend fun getPictureOfDay(
        @Query("api_key") apiKey: String = Constants.API_KEY
    ): PictureOfDay

    companion object {
        val retrofitService: ApiService by lazy {
            RetrofitClient.getInstance().create(ApiService::class.java)
        }
    }
}