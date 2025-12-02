package ru.practicum.android.diploma.di

import android.content.Context
import android.net.ConnectivityManager
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.practicum.android.diploma.BuildConfig
import ru.practicum.android.diploma.data.network.AuthInterceptor
import ru.practicum.android.diploma.data.network.VacanciesApiService
import ru.practicum.android.diploma.data.network.NetworkClient
import ru.practicum.android.diploma.data.network.NetworkClientImpl
import ru.practicum.android.diploma.data.network.NetworkStatusChecker
import ru.practicum.android.diploma.data.network.NetworkStatusCheckerImpl

// Базовый URL Practicum Vacancies API
private const val BASE_URL = "https://practicum-diploma-8bc38133faba.herokuapp.com/"

val networkModule = module {

    // Логгер
    single {
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    // AuthInterceptor c токеном из BuildConfig
    single {
        AuthInterceptor(
            accessToken = BuildConfig.API_ACCESS_TOKEN
        )
    }

    // OkHttpClient c авторизацией + логгером
    single {
        OkHttpClient.Builder()
            .addInterceptor(get<AuthInterceptor>())
            .addInterceptor(get<HttpLoggingInterceptor>())
            .build()
    }

    // Retrofit с правильным BASE_URL и клиентом
    single {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // API интерфейс
    single<VacanciesApiService> {
        get<Retrofit>().create(VacanciesApiService::class.java)
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

    // NetworkClient (оборачивает API + проверка сети) теперь зависит от NetworkStatusChecker
    single<NetworkClient> {
        NetworkClientImpl(
            apiService = get(),
            networkStatusChecker = get()
        )
    }
}
