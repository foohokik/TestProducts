package com.example.testproducts.worker

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.testproducts.data.api.ProductsAPI
import com.example.testproducts.di.IoDispatcher
import com.example.testproducts.domain.interactor.ProductInteractor
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltWorker
class Worker @AssistedInject constructor (
    private val productInteractor: ProductInteractor,
    @Assisted private val context: Context,
    @Assisted private val params: WorkerParameters
): CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        Log.d("WRK", "doWork")
          return  try {
                productInteractor.getAllData()
              Log.d("WRK", "get  ")
                Result.success()
            } catch (throwable: Throwable) {
                Result.failure()
            }
    }

}