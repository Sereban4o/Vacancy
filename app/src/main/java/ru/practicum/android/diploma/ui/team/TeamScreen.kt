package ru.practicum.android.diploma.ui.team

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.ui.components.DisplayTitle
import ru.practicum.android.diploma.ui.components.Heading
import ru.practicum.android.diploma.ui.components.TeamMembers
import ru.practicum.android.diploma.ui.theme.SpacerLarge
import ru.practicum.android.diploma.ui.theme.SpacerMedium

/**
 * Статические данные команды (по ТЗ)
 */
private val teamMembersDef = listOf(
    TeamMemberDef(
        nameRes = R.string.team_member_vitaliy,
        rolesRes = listOf(
            R.string.team_role_design,
            R.string.team_role_testing,
            R.string.team_role_development
        )
    ),
    TeamMemberDef(
        nameRes = R.string.team_member_sergey,
        rolesRes = listOf(
            R.string.team_role_team_lead,
            R.string.team_role_review,
            R.string.team_role_design,
            R.string.team_role_testing,
            R.string.team_role_development
        )
    ),
    TeamMemberDef(
        nameRes = R.string.team_member_andrey,
        rolesRes = listOf(
            R.string.team_role_design,
            R.string.team_role_testing,
            R.string.team_role_development
        )
    ),
    TeamMemberDef(
        nameRes = R.string.team_member_amanzhol,
        rolesRes = listOf(
            R.string.team_role_design,
            R.string.team_role_testing,
            R.string.team_role_development
        )
    ),
)

@Composable
fun TeamScreen() {
    val context = LocalContext.current

    // Собираем строки вида:
    // "Имя • Роль1, Роль2, ..."
    val memberLines = remember(context) {
        val res = context.resources
        teamMembersDef
            .map { def ->
                val name = res.getString(def.nameRes)
                val roles = def.rolesRes
                    .joinToString(", ") { roleId -> res.getString(roleId) }
                "$name • $roles"
            }
            .toTypedArray()
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        // Заголовок экрана "Команда"
        Heading(text = stringResource(R.string.team))

        Spacer(modifier = Modifier.height(SpacerMedium))

        // Большой заголовок "Над приложением работали"
        DisplayTitle(text = stringResource(R.string.team_title))

        Spacer(modifier = Modifier.height(SpacerLarge))

        // Сам список
        TeamMembers(listOfMembers = memberLines)
    }
}

/**
 * Модель одного члена команды:
 *  - имя (ресурс)
 *  - список ролей (тоже ресурсы)
 */
private data class TeamMemberDef(
    @StringRes val nameRes: Int,
    @StringRes val rolesRes: List<Int>
)

@Preview(showBackground = true)
@Composable
private fun TeamScreenPreview() {
    MaterialTheme {
        TeamScreen()
    }
}
