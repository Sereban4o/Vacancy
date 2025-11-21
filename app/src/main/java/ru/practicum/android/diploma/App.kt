package ru.practicum.android.diploma

import android.app.Application
import android.util.Log

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        // место Koin, Room, и т.п.

        // 🧼 Ловим крэш-ошибки
        Thread.setDefaultUncaughtExceptionHandler { thread, throwable ->
            Log.e("UncaughtException", "Uncaught exception in thread ${thread.name}", throwable)
        }
    }
}
