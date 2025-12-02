package ru.practicum.android.diploma

import android.app.Application
import android.util.Log
import coil.ImageLoader
import coil.ImageLoaderFactory
import filterModule
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import ru.practicum.android.diploma.di.convertersModule
import ru.practicum.android.diploma.di.databaseModule
import ru.practicum.android.diploma.di.interactorModule
import ru.practicum.android.diploma.di.networkModule
import ru.practicum.android.diploma.di.repositoryModule
import ru.practicum.android.diploma.di.viewModelModule

class App : Application(), ImageLoaderFactory {

    override fun newImageLoader(): ImageLoader {
        return ImageLoader.Builder(this)
            .okHttpClient {
                OkHttpClient.Builder()
                    .addInterceptor { chain ->
                        val newRequest = chain.request()
                            .newBuilder()
                            .header(
                                "User-Agent",
                                "Mozilla/5.0 (Android; VacancyApp) Safari/537.36"
                            )
                            .build()
                        chain.proceed(newRequest)
                    }
                    .build()
            }
            .build()
    }

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
                filterModule,

                // сюда позже  модули presentation/ui и т.д.
            )
        }

        // 🧼 Ловим крэш-ошибки
        Thread.setDefaultUncaughtExceptionHandler { thread, throwable ->
            Log.e("UncaughtException", "Uncaught exception in thread ${thread.name}", throwable)
        }
    }
}
