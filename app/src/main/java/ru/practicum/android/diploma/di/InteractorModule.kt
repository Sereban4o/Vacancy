package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.domain.impl.CountriesInteractorImpl
import ru.practicum.android.diploma.domain.impl.FavoritesInteractorImpl
import ru.practicum.android.diploma.domain.impl.IndustriesInteractorImpl
import ru.practicum.android.diploma.domain.interactors.SearchVacanciesInteractor
import ru.practicum.android.diploma.domain.interactors.FavoritesInteractor
import ru.practicum.android.diploma.domain.impl.SearchVacanciesInteractorImpl
import ru.practicum.android.diploma.domain.interactors.CountriesInteractor
import ru.practicum.android.diploma.domain.interactors.IndustriesInteractor
import ru.practicum.android.diploma.domain.interactors.VacancyDetailsInteractor
import ru.practicum.android.diploma.domain.repository.VacanciesRepository

val interactorModule = module {

    single<SearchVacanciesInteractor> {
        SearchVacanciesInteractorImpl(
            vacanciesRepository = get()
        )
    }

    single {
        VacancyDetailsInteractor(
            repository = get<VacanciesRepository>()
        )
    }

    single<FavoritesInteractor> {
        FavoritesInteractorImpl(get())
    }

    single<IndustriesInteractor> {
        IndustriesInteractorImpl(
            remoteDataSource = get()
        )
    }

    single<CountriesInteractor> {
        CountriesInteractorImpl(
            remoteDataSource = get()
        )
    }
}
