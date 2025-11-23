package ru.practicum.android.diploma.data.network

import okhttp3.Interceptor
import okhttp3.Response

/**
 * Интерсептор, который добавляет токен авторизации
 * ко всем HTTP-запросам к Vacancies API.
 */
class AuthInterceptor(
    private val accessToken: String
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        val newRequest = originalRequest.newBuilder()
            // согласно доке: Authorization: <token>
            .addHeader("Authorization", accessToken)
            .build()

        return chain.proceed(newRequest)
    }
}
