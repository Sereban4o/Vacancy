package ru.practicum.android.diploma.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf
import ru.practicum.android.diploma.presentation.vacancyDetailsScreen.VacancyDetailsViewModel
import ru.practicum.android.diploma.ui.vacancydetails.VacancyDetailsScreen
import ru.practicum.android.diploma.ui.favorites.FavouritesScreen
import ru.practicum.android.diploma.ui.main.MainScreen
import ru.practicum.android.diploma.ui.team.TeamScreen
import ru.practicum.android.diploma.util.Routes

// Моковый ID для теста
private const val MOCK_VACANCY_ID = "0000b92d-9ad9-44aa-8b45-5bf85d8ea9a0"
// Реальный ID для API-теста (любой ID из /vacancies)
private const val REAL_VACANCY_ID = "0001266a-3da9-4af8-b384-2377f0ea5453"

@Composable
fun NavGraph(
    modifier: Modifier,
    startDestination: String = Routes.Main.name,
    navHostController: NavHostController
) {
    NavHost(
        navController = navHostController,
        startDestination = startDestination
    ) {

        // --- Main screen ---
        composable(route = Routes.Main.name) {

            MainScreen(
                modifier = modifier,

                // Кнопка 1 — мок
                onOpenMockVacancy = {
                    navHostController.navigate("$VACANCY_DETAILS_ROUTE/$MOCK_VACANCY_ID")
                },

                // Кнопка 2 — API
                onOpenRealVacancy = {
                    navHostController.navigate("$VACANCY_DETAILS_ROUTE/$REAL_VACANCY_ID")
                }
            )
        }

        // --- Favorites ---
        composable(route = Routes.Favorites.name) {
            FavouritesScreen(modifier)
        }

        // --- Team ---
        composable(route = Routes.Team.name) {
            TeamScreen(modifier)
        }

        // --- Vacancy Details ---
        composable(
            route = "$VACANCY_DETAILS_ROUTE/{$ARG_VACANCY_ID}",
            arguments = listOf(
                navArgument(ARG_VACANCY_ID) {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->

            val vacancyId = backStackEntry
                .arguments
                ?.getString(ARG_VACANCY_ID)
                .orEmpty()

            val viewModel: VacancyDetailsViewModel = koinViewModel(
                parameters = { parametersOf(vacancyId) }
            )

            VacancyDetailsScreen(
                vacancyId = vacancyId,
                modifier = modifier,
                onBack = { navHostController.popBackStack() },
                viewModel = viewModel
            )
        }
    }
}
