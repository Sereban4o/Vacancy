package ru.practicum.android.diploma.di

import android.content.Context
import android.net.ConnectivityManager
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.practicum.android.diploma.data.network.ApiService
import ru.practicum.android.diploma.data.network.NetworkClient
import ru.practicum.android.diploma.data.network.NetworkClientImpl
import ru.practicum.android.diploma.util.NetworkStatusChecker
import ru.practicum.android.diploma.util.NetworkStatusCheckerImpl

private const val BASE_URL = "https://example.com/" // TODO: заменить на реальный baseUrl API

val networkModule = module {

    single {
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    single {
        OkHttpClient.Builder()
            .addInterceptor(get<HttpLoggingInterceptor>())
            .build()
    }

    single {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single<ApiService> {
        get<Retrofit>().create(ApiService::class.java)
    }

    // ConnectivityManager из системного сервиса
    single<ConnectivityManager> {
        val context: Context = get()
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }

    // Реальный проверяльщик сети
    single<NetworkStatusChecker> {
        NetworkStatusCheckerImpl(
            connectivityManager = get()
        )
    }

    // NetworkClient теперь зависит от NetworkStatusChecker
    single<NetworkClient> {
        NetworkClientImpl(
            apiService = get(),
            networkStatusChecker = get()
        )
    }
}
