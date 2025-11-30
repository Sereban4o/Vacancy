package ru.practicum.android.diploma.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import org.koin.androidx.compose.koinViewModel
import ru.practicum.android.diploma.presentation.favorites.FavoritesViewModel
import ru.practicum.android.diploma.presentation.vacancydetails.VacancyDetailsViewModel
import ru.practicum.android.diploma.presentation.vacancydetails.VacancyDetailsViewModel.Companion.ARG_FROM_API
import ru.practicum.android.diploma.presentation.vacancydetails.VacancyDetailsViewModel.Companion.ARG_VACANCY_ID
import ru.practicum.android.diploma.ui.main.MainScreen
import ru.practicum.android.diploma.ui.team.TeamScreen
import ru.practicum.android.diploma.ui.favorites.FavouritesScreen
import ru.practicum.android.diploma.util.Routes
import ru.practicum.android.diploma.ui.details.VacancyDetailsScreen

@Composable
fun NavGraph(
    modifier: Modifier,
    startDestination: String = Routes.Main.name,
    navHostController: NavHostController
) {
    NavHost(
        navController = navHostController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        // 🟦 Главный экран
        composable(Routes.Main.name) {
            MainScreen(
                onFilterClick = { /* откроем фильтры позже */ },
                onVacancyClick = { id ->
                    // из поиска → fromApi = true (по умолчанию в VM)
                    navHostController.navigateToVacancyDetails(
                        vacancyId = id,
                        fromApi = true
                    )
                }
            )
        }

        // ⭐ Избранное
        composable(Routes.Favorites.name) {
            val vm: FavoritesViewModel = koinViewModel()

            FavouritesScreen(
                modifier = Modifier,
                viewModel = vm,
                onVacancyClick = { id ->
                    // из избранного → явно fromApi=false
                    navHostController.navigateToVacancyDetails(
                        vacancyId = id,
                        fromApi = false
                    )
                }
            )
        }

        // 👥 Команда
        composable(Routes.Team.name) {
            TeamScreen()
        }

        // 📄 Детали вакансии
        composable(
            // добавили query-параметр в route
            route = "${Routes.VacancyDetails.name}/{$ARG_VACANCY_ID}?$ARG_FROM_API={$ARG_FROM_API}",
            arguments = listOf(
                navArgument(ARG_VACANCY_ID) {
                    type = NavType.StringType
                },
                navArgument(ARG_FROM_API) {
                    type = NavType.BoolType
                    defaultValue = true // по умолчанию считаем, что грузим из API
                }
            )
        ) { _ ->

            val vm: VacancyDetailsViewModel = koinViewModel()

            VacancyDetailsScreen(
                modifier = Modifier,
                onBack = { navHostController.popBackStack() },
                viewModel = vm
            )
        }
    }
}
