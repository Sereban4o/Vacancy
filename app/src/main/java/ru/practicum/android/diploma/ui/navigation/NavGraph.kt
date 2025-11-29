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
                    // ⚠️ ВАЖНО:
                    // здесь fromApi не передаём — сработает defaultValue = true
                    // т.е. из поиска данные будут грузиться из API
                    navHostController.navigate("$VACANCY_DETAILS_ROUTE/$id")
                    // Можно и явно:
                    // navHostController.navigate("$VACANCY_DETAILS_ROUTE/$id?$ARG_FROM_API=true")
                }
            )
        }

        // ⭐ Избранное
        composable(Routes.Favorites.name) {
            // Сейчас просто экран без навигации по клику
            // Когда Андрей доделает FavouritesScreen, он сможет вызвать:
            //
            // navHostController.navigate("$VACANCY_DETAILS_ROUTE/$id?$ARG_FROM_API=false")
            //
            // и это будет означать "загружать данные из БД"
            FavouritesScreen(Modifier)
        }

        // 👥 Команда
        composable(Routes.Team.name) {
            TeamScreen()
        }

        // 📄 Детали вакансии
        composable(
            route = "$VACANCY_DETAILS_ROUTE/{$ARG_VACANCY_ID}",
            arguments = listOf(
                navArgument(ARG_VACANCY_ID) {
                    type = NavType.StringType
                },
                navArgument(ARG_FROM_API) {
                    type = NavType.BoolType
                    defaultValue = true // по умолчанию считаем, что грузим из API
                }
            )
        ) { backStackEntry ->

            val vacancyId = backStackEntry.arguments!!.getString(ARG_VACANCY_ID)!!
            val fromApi = backStackEntry.arguments?.getBoolean(ARG_FROM_API) ?: true

            // parametersOf(vacancyId, fromApi)
            val vm: VacancyDetailsViewModel = koinViewModel(
                parameters = { parametersOf(vacancyId, fromApi) }
            )

            VacancyDetailsScreen(
                modifier = Modifier,
                onBack = { navHostController.popBackStack() },
                viewModel = vm
            )
        }
    }
}
