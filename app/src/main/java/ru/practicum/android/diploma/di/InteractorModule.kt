package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.domain.interactors.SearchVacanciesInteractor

val interactorModule = module {

    single {
        SearchVacanciesInteractor(
            vacanciesRepository = get()
        )
    }
}
