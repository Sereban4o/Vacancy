package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.data.convertor.VacancyDbConvertor
import ru.practicum.android.diploma.data.network.VacanciesRemoteDataSource
import ru.practicum.android.diploma.data.network.VacanciesRemoteDataSourceImpl
import ru.practicum.android.diploma.data.repository.VacanciesRepositoryImpl
import ru.practicum.android.diploma.domain.impl.FavoritesRepositoryImpl
import ru.practicum.android.diploma.domain.repository.FavoritesRepository
import ru.practicum.android.diploma.domain.repository.VacanciesRepository

val repositoryModule = module {

    single<VacanciesRepository> {
        VacanciesRepositoryImpl(get()) // get() = VacanciesRemoteDataSource
    }

    single<VacanciesRemoteDataSource> {
        VacanciesRemoteDataSourceImpl(get()) // get() = NetworkClient
    }
    single<FavoritesRepository> {
        FavoritesRepositoryImpl(get(), get())
    }

    factory { VacancyDbConvertor() }
}
