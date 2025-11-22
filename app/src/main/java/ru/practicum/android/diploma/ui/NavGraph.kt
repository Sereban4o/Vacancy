import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ru.practicum.android.diploma.ui.favorites.FavouritesScreen
import ru.practicum.android.diploma.ui.main.MainScreen
import ru.practicum.android.diploma.ui.team.TeamScreen
import ru.practicum.android.diploma.util.Routes

@Composable
fun NavGraph(
    modifier: Modifier,
    startDestination: String = Routes.Main.name,
    navHostController: NavHostController,
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
    }
}



