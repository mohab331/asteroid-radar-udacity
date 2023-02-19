package com.udacity.asteroidradar.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.udacity.asteroidradar.data.api.ApiService
import com.udacity.asteroidradar.data.api.getDaySeven
import com.udacity.asteroidradar.data.api.getTodayDate
import com.udacity.asteroidradar.data.constants.Constants
import com.udacity.asteroidradar.data.database.AsteroidDatabase
import com.udacity.asteroidradar.data.database.PictureDatabase
import com.udacity.asteroidradar.data.model.Asteroid
import com.udacity.asteroidradar.data.model.PictureOfDay
import com.udacity.asteroidradar.repository.asteroidRepository.AsteroidRepository
import kotlinx.coroutines.launch

class MainViewModel (application: Application) : AndroidViewModel(application) {

    private val asteroidsDatabase = AsteroidDatabase.getInstance(application)
    private val pictureDatabase = PictureDatabase.getInstance(application)
    private val apiService = ApiService
    private val asteroidsRepository = AsteroidRepository(asteroidsDatabase.dao , pictureDatabase.dao , apiService.retrofitService)

    private val _asteroids = MutableLiveData<List<Asteroid>>()
    val asteroids: LiveData<List<Asteroid>>
        get() = _asteroids

    private val _pictureOfDay = MutableLiveData<PictureOfDay>()
    val pictureOfDay: LiveData<PictureOfDay>
        get() = _pictureOfDay

    private val _selectedAsteroid = MutableLiveData<Asteroid?>()
    val selectedAsteroid: LiveData<Asteroid?>
        get() = _selectedAsteroid

init {
    getPictureOfTheDay()
    getAllAsteroids()
}

     private fun getPictureOfTheDay() {
        viewModelScope.launch {
            try {
                _pictureOfDay.value = asteroidsRepository.getPictureOfDay(apiKey = Constants.API_KEY)
            } catch (ex: Exception) {
                Log.e("mainViewModel", "error : ${ex.message}")
            }
        }
    }

    fun getAllAsteroids() {
        viewModelScope.launch {
            try {
                _asteroids.value = asteroidsRepository.getAllAsteroidsFromDatabase()

                if (_asteroids.value?.isEmpty() == true) {
                    asteroidsRepository.refreshAsteroid(startDate = getTodayDate() , endDate = getDaySeven())
                    _asteroids.value = asteroidsRepository.getAllAsteroidsFromDatabase()
                }
            } catch (ex: Exception) {
                Log.e("mainViewModel", "error : ${ex.message}")
            }
        }
    }

    fun displayDetails(asteroid: Asteroid) {
        _selectedAsteroid.value = asteroid

    }

    fun navigateToDetailsComplete() {
        _selectedAsteroid.value = null
    }

    fun getTodayAsteroids() {
        viewModelScope.launch {
            try {
                _asteroids.value = asteroidsRepository.getAsteroidOfToday()
            } catch (ex: Exception) {
                Log.e("mainViewModel", "error : ${ex.message}")
            }
        }
    }

    fun getWeekAsteroids() {
        viewModelScope.launch {
            try {
                _asteroids.value = asteroidsRepository.getWeekAsteroids()
            } catch (ex: Exception) {
                Log.e("mainViewModel", "error : ${ex.message}")
            }
        }
    }
}
