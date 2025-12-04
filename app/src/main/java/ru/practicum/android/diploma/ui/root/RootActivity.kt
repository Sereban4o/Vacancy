package ru.practicum.android.diploma.ui.root

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import ru.practicum.android.diploma.ui.navigation.NavGraph
import ru.practicum.android.diploma.ui.theme.AppTheme
import ru.practicum.android.diploma.ui.theme.PaddingScreenHorizontal
import ru.practicum.android.diploma.util.Screen

class RootActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            AppTheme {
                Root()
            }
        }
    }
}

@Composable
fun Root() {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()

    // route может быть типа "VacancyDetails/{id}?fromApi={fromApi}"
    // поэтому берём только последнюю "часть" до '?'
    val rawRoute = backStackEntry?.destination?.route
    val normalizedRoute = rawRoute
        ?.substringBefore("?")
        ?.substringAfterLast("/")

    val showBottomBar = when (normalizedRoute) {
        Screen.Main.route,
        Screen.Favorites.route,
        Screen.Team.route -> true

        else -> false // VacancyDetails, Filter, WorkPlace и т.д. — без нижнего бара
    }

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                BottomNavigationBar(navController)
            }
        }
    ) { innerPadding ->
        NavGraph(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = PaddingScreenHorizontal)
                .background(MaterialTheme.colorScheme.background),
            navHostController = navController
        )
    }
}
