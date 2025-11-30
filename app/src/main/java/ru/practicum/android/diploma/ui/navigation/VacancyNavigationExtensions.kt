package ru.practicum.android.diploma.ui.navigation

import androidx.navigation.NavController
import ru.practicum.android.diploma.presentation.vacancydetails.VacancyDetailsViewModel.Companion.ARG_FROM_API
import ru.practicum.android.diploma.util.Routes

/**
 * Навигация на экран деталей вакансии.
 *
 * route: VacancyDetails/{vacancyId}?fromApi={fromApi}
 */
fun NavController.navigateToVacancyDetails(
    vacancyId: String,
    fromApi: Boolean = true
) {
    navigate("${Routes.VacancyDetails.name}/$vacancyId?$ARG_FROM_API=$fromApi") {
        launchSingleTop = true
    }
}
