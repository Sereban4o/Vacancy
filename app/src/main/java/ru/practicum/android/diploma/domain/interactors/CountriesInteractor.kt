package ru.practicum.android.diploma.domain.interactors

import ru.practicum.android.diploma.domain.models.FilterParameter

interface CountriesInteractor {
    suspend fun getCountries(): List<FilterParameter>
}
