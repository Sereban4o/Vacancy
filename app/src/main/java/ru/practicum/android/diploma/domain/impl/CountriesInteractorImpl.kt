package ru.practicum.android.diploma.domain.impl

import ru.practicum.android.diploma.data.network.VacanciesRemoteDataSource
import ru.practicum.android.diploma.domain.interactors.CountriesInteractor
import ru.practicum.android.diploma.domain.models.FilterParameter

class CountriesInteractorImpl(
    private val remoteDataSource: VacanciesRemoteDataSource
) : CountriesInteractor {

    override suspend fun getCountries(): List<FilterParameter> {
        // Practicum API: getAreas() возвращает список стран верхнего уровня
        val areas = remoteDataSource.getAreas()
        return areas.map { area ->
            FilterParameter(
                id = area.id,
                name = area.name
            )
        }
    }
}
