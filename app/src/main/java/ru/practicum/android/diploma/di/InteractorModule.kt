package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.domain.impl.VacancyInteractorImpl

val interactorModule = module {

    // Вариант 1: если есть интерфейс VacancyInteractor
    // single<VacancyInteractor> {
    //     VacancyInteractorImpl(repository = get())
    // }

    // Вариант 2: пока просто реализация
    single {
        VacancyInteractorImpl(
            repository = get()
        )
    }
}
