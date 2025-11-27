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
import ru.practicum.android.diploma.presentation.vacancydetails.VacancyDetailsViewModel
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
                    navHostController.navigate("$VACANCY_DETAILS_ROUTE/$id")
                }
            )
        }

        // ⭐ Избранное
        composable(Routes.Favorites.name) {
            FavouritesScreen()
        }

        // 👥 Команда
        composable(Routes.Team.name) {
            TeamScreen()
        }

        // 📄 Детали вакансии
        composable(
            route = "$VACANCY_DETAILS_ROUTE/{$ARG_VACANCY_ID}",
            arguments = listOf(navArgument(ARG_VACANCY_ID) {
                type = NavType.StringType
            })
        ) { backStackEntry ->

            val vacancyId = backStackEntry.arguments!!.getString(ARG_VACANCY_ID)!!
            val vm: VacancyDetailsViewModel = koinViewModel {
                parametersOf(vacancyId)
            }

            VacancyDetailsScreen(
//                vacancyId = vacancyId,
                modifier = modifier,
                onBack = { navHostController.popBackStack() },
                viewModel = vm
            )
        }
    }
}
