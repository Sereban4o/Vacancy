package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.domain.interactors.SearchVacanciesInteractor
import ru.practicum.android.diploma.domain.interactors.SearchVacanciesInteractorImpl
import ru.practicum.android.diploma.domain.interactors.VacancyDetailsInteractor
import ru.practicum.android.diploma.domain.repository.VacanciesRepository

/**
 * Модуль для DI (Koin), как и требует архитектура из методички:
 * Presentation -> Interactor -> Repository -> DataSource.
 */
val interactorModule = module {

    // Поиск вакансий с постраничной загрузкой (Paging 3)
    single<SearchVacanciesInteractor> {
        SearchVacanciesInteractorImpl(
            vacanciesRepository = get()
        )
    }

    // Детали вакансии (получение одной вакансии по id)
    single {
        VacancyDetailsInteractor(
            repository = get<VacanciesRepository>()
        )
    }
}
