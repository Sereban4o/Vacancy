package ru.practicum.android.diploma.ui.root

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.util.Routes

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val entry by navController.currentBackStackEntryAsState()
    val currentRoute = entry?.destination?.route

    Column {
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(colorResource(R.color.divider))
        )

        BottomAppBar(
            containerColor = MaterialTheme.colorScheme.background,
            tonalElevation = 0.dp,
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceAround,
                modifier = Modifier.fillMaxWidth()
            ) {
                BottomNavigationItem(
                    icon = painterResource(R.drawable.ic_main_24),
                    label = stringResource(R.string.main),
                    selected = currentRoute == Routes.Main.name,
                    onClick = {
                        navController.navigate(Routes.Main.name) {
                            launchSingleTop = true
                            restoreState = true
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                        }
                    }
                )

                BottomNavigationItem(
                    icon = painterResource(R.drawable.ic_favorites_24),
                    label = stringResource(R.string.favorites),
                    selected = currentRoute == Routes.Favorites.name,
                    onClick = {
                        navController.navigate(Routes.Favorites.name) {
                            launchSingleTop = true
                            restoreState = true
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                        }
                    }
                )

                BottomNavigationItem(
                    icon = painterResource(R.drawable.ic_team_24),
                    label = stringResource(R.string.team),
                    selected = currentRoute == Routes.Team.name,
                    onClick = {
                        navController.navigate(Routes.Team.name) {
                            launchSingleTop = true
                            restoreState = true
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                        }
                    }
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
    val color = if (selected) {
        colorResource(R.color.activeMenu)
    } else {
        colorResource(R.color.inActiveMenu)
    }

    Column(
        modifier = Modifier.clickable(onClick = onClick),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = icon,
            contentDescription = label,
            tint = color
        )
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall.copy(
                color = color,
                textAlign = TextAlign.Center
            ),
            maxLines = 1
        )
    }
}
