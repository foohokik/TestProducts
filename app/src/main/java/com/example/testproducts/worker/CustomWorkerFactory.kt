package com.example.testproducts.worker

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.example.testproducts.di.IoDispatcher
import com.example.testproducts.domain.interactor.ProductInteractor
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

// class CustomWorkerFactory @Inject constructor(
////     @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
//     private val productInteractor: ProductInteractor
//) : WorkerFactory () {
//     override fun createWorker(
//         appContext: Context,
//         workerClassName: String,
//         workerParameters: WorkerParameters
//     ): ListenableWorker = Worker(productInteractor,appContext,workerParameters)
//
// }
