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
import ru.practicum.android.diploma.presentation.filter.FilterViewModel
import ru.practicum.android.diploma.presentation.search.SearchViewModel
import ru.practicum.android.diploma.presentation.vacancydetails.VacancyDetailsViewModel
import ru.practicum.android.diploma.presentation.vacancydetails.VacancyDetailsViewModel.Companion.ARG_FROM_API
import ru.practicum.android.diploma.presentation.vacancydetails.VacancyDetailsViewModel.Companion.ARG_VACANCY_ID
import ru.practicum.android.diploma.ui.filter.country.CountryScreen
import ru.practicum.android.diploma.ui.details.VacancyDetailsScreen
import ru.practicum.android.diploma.ui.favorites.FavouritesScreen
import ru.practicum.android.diploma.ui.filter.FilterSettingsScreen
import ru.practicum.android.diploma.ui.filter.industry.IndustryScreen
import ru.practicum.android.diploma.ui.main.MainScreen
import ru.practicum.android.diploma.ui.filter.region.RegionScreen
import ru.practicum.android.diploma.ui.team.TeamScreen
import ru.practicum.android.diploma.ui.filter.workplace.WorkPlaceScreen
import ru.practicum.android.diploma.util.Screen

@Composable
fun NavGraph(
    modifier: Modifier = Modifier,
    startDestination: String = Screen.Main.route,
    navHostController: NavHostController
) {
    // 🔹 ОДИН общий экземпляр (Sergey note on SearchScreen refresh)
    val searchViewModel: SearchViewModel = koinViewModel()

    NavHost(
        navController = navHostController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        // 🟦 Главный экран
        composable(Screen.Main.route) {
            MainScreen(
                searchViewModel = searchViewModel,
                onFilterClick = { navHostController.navigate(Screen.FilterSettings.route) },
                onVacancyClick = { id ->
                    navHostController.navigateToVacancyDetails(id, true)
                }
            )
        }

        // Фильтр 🔹
        composable(Screen.FilterSettings.route) {
            val filterViewModel: FilterViewModel = koinViewModel()

            FilterSettingsScreen(
                onBack = { navHostController.popBackStack() }, // для стрелки "Назад"
                onWorkPlaceClick = {
                    navHostController.navigate(Screen.WorkPlace.route)
                },
                onIndustryClick = {
                    navHostController.navigate(Screen.Industry.route)
                },
                onApply = {
                    // 1️⃣ применяем фильтры (обновляем флаг и, при необходимости, перезапускаем поиск)
                    searchViewModel.onFiltersApplied()
                    // 2️⃣ возвращаемся на экран поиска
                    navHostController.popBackStack()
                },
                viewModel = filterViewModel
            )
        }

        // Страна 🔹
        composable(Screen.Country.route) {
            CountryScreen(
                onBack = { navHostController.popBackStack() },
            )
        }

        // Регион 🔹
        composable(Screen.Region.route) {
            RegionScreen(
                onBack = { navHostController.popBackStack() }
            )
        }

        // Отрасли 🔹
        composable(Screen.Industry.route) {
            IndustryScreen(
                onBack = { navHostController.popBackStack() }
            )
        }

        // Место работы 🔹
        composable(Screen.WorkPlace.route) {
            WorkPlaceScreen(
                onBack = { navHostController.popBackStack() },
                onCountryClick = {
                    navHostController.navigate(Screen.Country.route)
                },
                onRegionClick = {
                    navHostController.navigate(Screen.Region.route)
                }
            )
        }

        // ⭐ Избранное
        composable(Screen.Favorites.route) {
            val vm: FavoritesViewModel = koinViewModel()
            FavouritesScreen(
                viewModel = vm,
                onVacancyClick = { id ->
                    navHostController.navigateToVacancyDetails(id, false)
                }
            )
        }

        // 👥 Команда
        composable(Screen.Team.route) {
            TeamScreen()
        }

        // 📄 Детали вакансии
        composable(
            route = "${Screen.VacancyDetails.route}/{$ARG_VACANCY_ID}?$ARG_FROM_API={$ARG_FROM_API}",
            arguments = listOf(
                navArgument(ARG_VACANCY_ID) { type = NavType.StringType },
                navArgument(ARG_FROM_API) {
                    type = NavType.BoolType
                    defaultValue = true
                }
            )
        ) {
            val vm: VacancyDetailsViewModel = koinViewModel()
            VacancyDetailsScreen(
                onBack = { navHostController.popBackStack() },
                viewModel = vm
            )
        }
    }
}
