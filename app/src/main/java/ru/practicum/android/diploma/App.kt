package ru.practicum.android.diploma

import android.app.Application
import android.util.Log
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import ru.practicum.android.diploma.di.convertersModule
import ru.practicum.android.diploma.di.databaseModule
import ru.practicum.android.diploma.di.networkModule
import ru.practicum.android.diploma.di.repositoryModule
import ru.practicum.android.diploma.di.interactorModule
import ru.practicum.android.diploma.di.storageModule
import ru.practicum.android.diploma.di.viewModelModule

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        // место Koin, Room, и т.п.
        startKoin {
            androidContext(this@App)
            modules(
                networkModule,
                databaseModule,
                repositoryModule,
                interactorModule,
                viewModelModule,
                convertersModule,
                storageModule,

                // сюда позже  модули presentation/ui и т.д.
            )
        }

        // 🧼 Ловим крэш-ошибки
        Thread.setDefaultUncaughtExceptionHandler { thread, throwable ->
            Log.e("UncaughtException", "Uncaught exception in thread ${thread.name}", throwable)
        }
    }
}
