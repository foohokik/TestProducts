package com.example.testproducts

import android.app.Application
import android.util.Log
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.testproducts.worker.Worker
import dagger.hilt.android.HiltAndroidApp
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltAndroidApp
class App: Application(), Configuration.Provider  {

    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setMinimumLoggingLevel(Log.DEBUG)
            .setWorkerFactory(workerFactory)
            .build()

    override fun onCreate() {
        super.onCreate()
        work()
    }



    private val constraints = Constraints.Builder()
        .setRequiredNetworkType(NetworkType.CONNECTED)
        .setRequiresBatteryNotLow(true)
        .setRequiresDeviceIdle(true)
        .build()

    private val repeatingRequest = PeriodicWorkRequestBuilder<Worker>(24,  TimeUnit.HOURS)
        .setConstraints(constraints)
        .addTag(WORK_MANAGER_DOWNLOAD_TAG)
        .build()

   private fun work() {
        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            WORK_MANAGER_DOWNLOAD_TAG,
            ExistingPeriodicWorkPolicy.KEEP,
            repeatingRequest
        )
    }

    companion object {
        const val WORK_MANAGER_DOWNLOAD_TAG = "WORK_MANAGER_DOWNLOAD_TAG"
    }


}