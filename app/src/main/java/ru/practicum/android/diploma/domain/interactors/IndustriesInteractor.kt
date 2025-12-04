package ru.practicum.android.diploma.domain.interactors

import ru.practicum.android.diploma.domain.models.FilterParameter

/**
 * Interactor для отраслей.
 */
interface IndustriesInteractor {
    suspend fun getIndustries(): List<FilterParameter>
}
