package ru.practicum.android.diploma.di

import android.content.Context
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import ru.practicum.android.diploma.data.filter.FilterPreferencesDataSource
import ru.practicum.android.diploma.data.filter.FilterPreferencesDataSourceImpl
import ru.practicum.android.diploma.data.filter.FilterSettingsRepositoryImpl
import ru.practicum.android.diploma.domain.interactors.FilterSettingsInteractor
import ru.practicum.android.diploma.domain.repository.FilterSettingsRepository

val filterModule = module {

    // SharedPreferences для фильтров
    single {
        androidContext().getSharedPreferences(
            "filter_prefs",
            Context.MODE_PRIVATE
        )
    }

    // DataSource над SharedPreferences (интерфейс -> реализация)
    single<FilterPreferencesDataSource> {
        FilterPreferencesDataSourceImpl(
            sharedPreferences = get()
        )
    }

    // Repository
    single<FilterSettingsRepository> {
        FilterSettingsRepositoryImpl(
            dataSource = get()
        )
    }

    // Interactor (если FilterSettingsInteractor — класс, оставляем так)
    single {
        FilterSettingsInteractor(
            repository = get()
        )
    }
}
