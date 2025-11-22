package ru.practicum.android.diploma.ui.root

import NavGraph
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import ru.practicum.android.diploma.BuildConfig
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.util.Routes

class RootActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Root()
        }

        // Пример использования access token для HeadHunter API
        networkRequestExample(accessToken = BuildConfig.API_ACCESS_TOKEN)
    }

    private fun networkRequestExample(accessToken: String) {
        // ...
    }

    @Composable
    fun Root() {
        val navController = rememberNavController()
        val topPadding = WindowInsets.statusBars.asPaddingValues().calculateTopPadding()
        val bottomPadding = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()
        Column(
            Modifier
                .fillMaxSize()
                .padding(bottom = bottomPadding, top = topPadding)
        ) {
            Scaffold(
                bottomBar = {
                    BottomNavigationBar(navController)
                }
            ) {
                NavGraph(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    navHostController = navController
                )
            }
        }

    }
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    Column() {
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(colorResource(R.color.divider))
        )
        BottomAppBar(containerColor = colorResource(R.color.container)) {
            Row(horizontalArrangement = Arrangement.SpaceAround, modifier = Modifier.fillMaxWidth()) {
                BottomNavigationItem(
                    icon = painterResource(R.drawable.main_menu),
                    label = stringResource(R.string.main),
                    selected = currentDestination?.route == Routes.Main.name,
                    onClick = { navController.navigate(Routes.Main.name) }
                )

                BottomNavigationItem(
                    icon = painterResource(R.drawable.favorites_menu),
                    label = stringResource(R.string.favorites),
                    selected = currentDestination?.route == Routes.Favorites.name,
                    onClick = { navController.navigate(Routes.Favorites.name) }
                )

                BottomNavigationItem(
                    icon = painterResource(R.drawable.team_menu),
                    label = stringResource(R.string.team),
                    selected = currentDestination?.route == Routes.Team.name,
                    onClick = { navController.navigate(Routes.Team.name) }
                )
            }
        }
    }
}

@Composable
fun BottomNavigationItem(
    icon: Painter,
    label: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    var color = colorResource(R.color.inActiveMenu)

    if (selected) {
        color = colorResource(R.color.activeMenu)
    }

    Column(modifier = Modifier.clickable(onClick = onClick), horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(painter = icon, contentDescription = label, tint = color)
        Text(
            text = label, style = TextStyle(
                color = color,
                fontFamily = FontFamily(Font(R.font.ys_display_regular)),
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp
            )
        )

    }
}
