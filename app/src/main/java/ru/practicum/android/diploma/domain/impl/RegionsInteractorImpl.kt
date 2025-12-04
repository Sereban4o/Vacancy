package ru.practicum.android.diploma.domain.impl

import ru.practicum.android.diploma.data.dto.FilterAreaDto
import ru.practicum.android.diploma.data.network.VacanciesRemoteDataSource
import ru.practicum.android.diploma.domain.interactors.RegionsInteractor
import ru.practicum.android.diploma.domain.models.FilterParameter

class RegionsInteractorImpl(
    private val remoteDataSource: VacanciesRemoteDataSource
) : RegionsInteractor {

    override suspend fun getRegionsForCountry(countryId: String?): List<FilterParameter> {
        val allAreas: List<FilterAreaDto> = remoteDataSource.getAreas()

        return if (countryId != null) {
            // 🔹 Есть выбранная страна → берём только её регионы
            val country = allAreas.firstOrNull { it.id == countryId } ?: return emptyList()

            country.areas.orEmpty()
                .map { area ->
                    FilterParameter(
                        id = area.id,
                        name = area.name
                    )
                }
        } else {
            // 🔹 Страна не выбрана → показываем все регионы,
            // которые являются дочерними по отношению к странам
            allAreas
                .flatMap { country -> country.areas.orEmpty() }
                .map { area ->
                    FilterParameter(
                        id = area.id,
                        name = area.name
                    )
                }
        }
    }

    override suspend fun getCountryForRegion(regionId: String): FilterParameter? {
        val allAreas: List<FilterAreaDto> = remoteDataSource.getAreas()

        // ищем страну, у которой среди children есть регион с таким id
        allAreas.forEach { country ->
            val foundRegion = country.areas.orEmpty().firstOrNull { it.id == regionId }
            if (foundRegion != null) {
                return FilterParameter(
                    id = country.id,
                    name = country.name
                )
            }
        }

        return null
    }
}
