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
import ru.practicum.android.diploma.util.Routes

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
    val currentRoute = backStackEntry?.destination?.route

    // определяем, показывать ли нижнюю навигацию
    val showBottomBar = when {
        currentRoute?.startsWith(Routes.VacancyDetails.name) == true -> false
        currentRoute == Routes.Main.name -> true
        currentRoute == Routes.Favorites.name -> true
        currentRoute == Routes.Team.name -> true
        else -> false
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
