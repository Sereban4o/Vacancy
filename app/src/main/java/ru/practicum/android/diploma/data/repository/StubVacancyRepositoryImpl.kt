package ru.practicum.android.diploma.data.repository

import ru.practicum.android.diploma.data.network.NetworkClient
import ru.practicum.android.diploma.domain.api.StubVacancyRepository
import ru.practicum.android.diploma.domain.models.StubVacancy

/**
 * Базовая реализация репозитория.
 * Пока возвращает заглушку, позже здесь будут реальные вызовы API и БД.
 */
class StubVacancyRepositoryImpl(
    private val networkClient: NetworkClient
) : StubVacancyRepository {

    override suspend fun getStubVacancies(): List<StubVacancy> {
        // Пример использования NetworkClient + ApiService:
        // networkClient.execute { ping() }

        return emptyList()
    }
}
