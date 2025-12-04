package ru.practicum.android.diploma.domain.impl

import ru.practicum.android.diploma.data.network.VacanciesRemoteDataSource
import ru.practicum.android.diploma.domain.interactors.IndustriesInteractor
import ru.practicum.android.diploma.domain.models.FilterParameter

class IndustriesInteractorImpl(
    private val remoteDataSource: VacanciesRemoteDataSource
) : IndustriesInteractor {

    override suspend fun getIndustries(): List<FilterParameter> {
        val dtoList = remoteDataSource.getIndustries()
        return dtoList.map { dto ->
            FilterParameter(
                id = dto.id,
                name = dto.name
            )
        }
    }
}
