package ru.practicum.android.diploma.domain.interactors

import ru.practicum.android.diploma.domain.models.FilterParameter

interface RegionsInteractor {
    /**
     * Возвращает:
     *  - если countryId != null → только регионы этой страны
     *  - если countryId == null → все регионы, которые НЕ являются странами
     */
    suspend fun getRegionsForCountry(
        countryId: String?
    ): List<FilterParameter>

    /**
     * Найти страну по региону (для авто-подстановки,
     * когда страна ещё не выбрана на момент клика).
     */
    suspend fun getCountryForRegion(regionId: String): FilterParameter?
}
