package com.udacity.asteroidradar.asteroidApp

import android.app.Application
import androidx.work.*
import com.udacity.asteroidradar.worker.WeeklyWorker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class AsteroidRadarApp : Application() {
    private val appScope = CoroutineScope(Dispatchers.Default)

    override fun onCreate() {
        super.onCreate()
        init()
    }

    private fun init() {
        appScope.launch {
            val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.UNMETERED)
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .setRequiresCharging(true)
                .setRequiresBatteryNotLow(true)
                .build()

            val repeatRequest = PeriodicWorkRequestBuilder<WeeklyWorker>(
                1,
                TimeUnit.DAYS,
            )
                .setConstraints(constraints)
                .build()


            WorkManager.getInstance().enqueueUniquePeriodicWork(
                "AsteroidsWorkManager",
                ExistingPeriodicWorkPolicy.KEEP,
                repeatRequest
            )
        }
    }
}