package com.kotian.countryview.data


import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.kotian.countryview.model.Country
import com.kotian.countryview.network.ApiService

import java.io.IOException

class CountryRepository(
    private val apiService: ApiService,
    private val context: Context
) {
    suspend fun getCountries(): Result<List<Country>> = try {
        if (!isNetworkAvailable()) throw IOException("No internet connection")
        Result.success(apiService.getCountries())
    } catch (e: Exception) {
        Result.failure(e)
    }

    private fun isNetworkAvailable(): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(network)
        return capabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true
    }
}