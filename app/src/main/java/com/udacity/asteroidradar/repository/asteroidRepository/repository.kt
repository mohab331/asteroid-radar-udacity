package com.udacity.asteroidradar.repository.asteroidRepository


import android.util.Log
import androidx.lifecycle.LiveData
import com.udacity.asteroidradar.data.api.ApiService
import com.udacity.asteroidradar.data.api.getDaySeven
import com.udacity.asteroidradar.data.api.getTodayDate
import com.udacity.asteroidradar.data.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.data.constants.Constants
import com.udacity.asteroidradar.data.database.AsteroidsDao
import com.udacity.asteroidradar.data.database.PicturesDao
import com.udacity.asteroidradar.data.model.Asteroid
import com.udacity.asteroidradar.data.model.PictureOfDay
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject


class AsteroidRepository(private val dao: AsteroidsDao,private val pictureDao: PicturesDao ,private val apiService: ApiService) {

    suspend fun refreshAsteroid(
        startDate: String = getTodayDate(),
        endDate: String = getDaySeven(),
        apiKey: String = Constants.API_KEY,
    ): List<Asteroid> {
        var asteroidList = mutableListOf<Asteroid>()
        try {
            val response = apiService.getAsteroids(startDate, endDate, apiKey)

            if (response.contentLength().compareTo(0) != 0) {
                    asteroidList = parseAsteroidsJsonResult(JSONObject(response.string()))

                withContext(Dispatchers.IO) {
                    dao.insert(asteroidList)
                }
            }
        } catch (exception: Exception) {
            Log.e("Repository", exception.message.toString())
        }
        return asteroidList
    }

    suspend fun getPictureOfDay(apiKey: String): PictureOfDay {
        var picture = PictureOfDay(
            "TEST",
            "image",
            "https://apod.nasa.gov/apod/image/2109/NGC7000_SHO_AndrewKlinger_res65_sig1024.jpg",
        )
        try {
                picture = apiService.getPictureOfDay(apiKey = apiKey)
            withContext(Dispatchers.IO) {
                pictureDao.deleteAll()
                pictureDao.insertPicture(picture)
            }
        } catch (exception: Exception) {
            Log.e("Repository", exception.message.toString())
        }
        return picture
    }

    suspend fun deleteAsteroidsFromDatabase() {
        withContext(Dispatchers.IO) {
            dao.deleteAll()
        }
    }

    suspend fun getAllAsteroidsFromDatabase(): List<Asteroid> {
        return withContext(Dispatchers.IO) {
            dao.getAllAsteroids()
        }
    }

    suspend fun getPictureFromDatabase(): PictureOfDay {
        return withContext(Dispatchers.IO) {
            pictureDao.getPicture()
        }
    }

    suspend fun getAsteroidOfToday(): List<Asteroid> {
        return withContext(Dispatchers.IO) {
            dao.getAsteroidsOfToday(
                date = getTodayDate()
            )
        }
    }


    suspend fun getWeekAsteroids(): List<Asteroid> {
        return withContext(Dispatchers.IO) {
            dao.getAsteroidsOfTheWeek(
                startDate = getTodayDate(),
                endDate = getDaySeven()
            )
        }
    }
}