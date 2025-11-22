package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.data.repository.StubVacancyRepositoryImpl
import ru.practicum.android.diploma.domain.api.StubVacancyRepository

val repositoryModule = module {

    single<StubVacancyRepository> {
        StubVacancyRepositoryImpl(
            networkClient = get()
        )
    }
}
