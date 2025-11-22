package ru.practicum.android.diploma.util

import android.net.ConnectivityManager
import android.net.NetworkCapabilities

/**
 * Интерфейс для проверки состояния сети.
 */
interface NetworkStatusChecker {
    fun isConnected(): Boolean
}

/**
 * Реализация через ConnectivityManager.
 */
class NetworkStatusCheckerImpl(
    private val connectivityManager: ConnectivityManager
) : NetworkStatusChecker {

    override fun isConnected(): Boolean {
        val network = connectivityManager.activeNetwork
        val capabilities = network?.let { connectivityManager.getNetworkCapabilities(it) }

        return capabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true
    }
}

