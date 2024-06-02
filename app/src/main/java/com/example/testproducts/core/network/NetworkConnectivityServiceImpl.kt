package com.example.testproducts.core.network

import android.content.Context
import android.net.ConnectivityManager
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class NetworkConnectivityServiceImpl @Inject constructor(
    @ApplicationContext context: Context
) : NetworkConnectivityService {

    private val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    override val isConnected: Boolean
        get() = connectivityManager.activeNetwork != null

}