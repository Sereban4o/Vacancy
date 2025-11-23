package ru.practicum.android.diploma.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import ru.practicum.android.diploma.ui.details.VacancyDetailsScreen
import ru.practicum.android.diploma.ui.favorites.FavouritesScreen
import ru.practicum.android.diploma.ui.main.MainScreen
import ru.practicum.android.diploma.ui.team.TeamScreen
import ru.practicum.android.diploma.util.Routes

@Composable
fun NavGraph(
    modifier: Modifier,
    startDestination: String = Routes.Main.name,
    navHostController: NavHostController
) {
    NavHost(navController = navHostController, startDestination = startDestination) {
        composable(route = Routes.Main.name) {
            MainScreen(modifier)
        }
        composable(route = Routes.Favorites.name) {
            FavouritesScreen(modifier)
        }
        composable(route = Routes.Team.name) {
            TeamScreen(modifier)
        }

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

            VacancyDetailsScreen(
                vacancyId = vacancyId,
                modifier = modifier
            )
        }
    }
}
