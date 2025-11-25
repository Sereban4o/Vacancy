package ru.practicum.android.diploma.ui.team

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.ui.components.DisplayTitle
import ru.practicum.android.diploma.ui.components.Heading
import ru.practicum.android.diploma.ui.components.TeamMembers
import ru.practicum.android.diploma.ui.theme.SpacerLarge
import ru.practicum.android.diploma.ui.theme.SpacerMedium

@Composable
fun TeamScreen(modifier: Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Heading(Modifier, stringResource(R.string.team))
        Spacer(modifier = Modifier.height(SpacerMedium))
        DisplayTitle(text = stringResource(R.string.team_title))
        Spacer(modifier = Modifier.height(SpacerLarge))
        TeamMembers(
            listOfMembers = stringArrayResource(R.array.team_members)
        )
    }
}

