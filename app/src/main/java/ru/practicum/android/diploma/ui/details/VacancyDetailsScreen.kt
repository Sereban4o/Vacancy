package ru.practicum.android.diploma.ui.details

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.models.VacancyContacts
import ru.practicum.android.diploma.domain.models.VacancyDetails
import ru.practicum.android.diploma.presentation.vacancydetails.VacancyDetailsUiState
import ru.practicum.android.diploma.presentation.vacancydetails.VacancyDetailsViewModel
import ru.practicum.android.diploma.ui.components.Heading
import ru.practicum.android.diploma.ui.components.InfoState
import ru.practicum.android.diploma.ui.components.formatSalary
import ru.practicum.android.diploma.ui.theme.CompanyCardBackgroundColor
import ru.practicum.android.diploma.ui.theme.TextColorLight
import ru.practicum.android.diploma.util.TypeState

@Composable
fun VacancyDetailsScreen(
    modifier: Modifier = Modifier,
    onBack: () -> Unit,
    viewModel: VacancyDetailsViewModel
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        // 🧩 Шапка экрана — всегда одна и та же, независимо от состояния
        Heading(
            text = stringResource(R.string.vacancy),
            leftBlock = {
                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .padding(end = 4.dp)
                        .clickable(onClick = onBack),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_arrow_back_24),
                        contentDescription = "Назад",
                        modifier = Modifier.size(24.dp),
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                }
                Spacer(Modifier.width(4.dp))
            },
            rightBlock = {
                Row {
                    // Кнопка "Поделиться" – работает только, когда есть контент
                    val vacancy =
                        (uiState as? VacancyDetailsUiState.Content)?.vacancy
                    IconButton(
                        onClick = {
                            vacancy?.let {
                                shareVacancy(context, it.vacancyUrl)
                            }
                        },
                        enabled = vacancy != null
                    ) {
                        Icon(
                            painterResource(R.drawable.ic_share_18_20),
                            contentDescription = "Поделиться",
                            tint = MaterialTheme.colorScheme.onBackground
                        )
                    }

                    IconButton(onClick = { /* TODO: избранное */ }) {
                        Icon(
                            painterResource(R.drawable.ic_favorites_22_20),
                            contentDescription = "Избранное",
                            tint = MaterialTheme.colorScheme.onBackground
                        )
                    }
                }
            }
        )

        Spacer(Modifier.height(8.dp))

        // 🔻 Дальше — содержимое экрана в зависимости от состояния
        when (uiState) {
            is VacancyDetailsUiState.Loading -> {
                Box(
                    Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            is VacancyDetailsUiState.Error -> {
                val error = uiState as VacancyDetailsUiState.Error
                Box(
                    Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    if (error.isNetworkError) {
                        InfoState(TypeState.NoInternet)
                    } else {
                        InfoState(TypeState.ServerErrorVacancy)
                    }
                }
            }

            is VacancyDetailsUiState.NoVacancy -> {
                Box(
                    Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    InfoState(TypeState.NoVacancy)
                }
            }

            is VacancyDetailsUiState.Content -> {
                val vacancy = (uiState as VacancyDetailsUiState.Content).vacancy
                VacancyDetailsContent(
                    vacancy = vacancy,
                    onEmailClick = { email -> openEmail(context, email) },
                    onPhoneClick = { phone -> openPhone(context, phone) },
                    modifier = modifier
                )
            }
        }
    }
}

@Composable
fun VacancyDetailsContent(
    vacancy: VacancyDetails,
    onEmailClick: (String) -> Unit,
    onPhoneClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        // 🔹 Главный заголовок вакансии — Bold/32
        Text(
            text = vacancy.title,
            style = MaterialTheme.typography.displayLarge,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(Modifier.height(8.dp))

        // 💰 Зарплата — Medium/22
        Text(
            text = formatSalary(vacancy.salaryFrom, vacancy.salaryTo, vacancy.currency),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(Modifier.height(16.dp))

        // 🏢 Компания + город
        CompanyCard(vacancy)

        Spacer(Modifier.height(24.dp))

        // 📌 Требуемый опыт
        Text(
            text = "Требуемый опыт",
            style = MaterialTheme.typography.labelMedium, // Medium/16
            color = MaterialTheme.colorScheme.onBackground
        )
        Spacer(Modifier.height(4.dp))

        vacancy.experience?.let {
            Text(
                text = it,
                style = MaterialTheme.typography.labelMedium, // Medium/16
                color = MaterialTheme.colorScheme.onBackground
            )
        }

        Spacer(Modifier.height(8.dp))

        Text(
            text = "${vacancy.employment}, ${vacancy.schedule}",
            style = MaterialTheme.typography.bodyMedium, // Regular/16
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(Modifier.height(24.dp))

        // 📝 Описание вакансии
        Text(
            text = "Описание вакансии",
            style = MaterialTheme.typography.titleMedium, // Medium/22
            color = MaterialTheme.colorScheme.onBackground
        )
        Spacer(Modifier.height(8.dp))
        DescriptionBlock(vacancy.description)

        Spacer(Modifier.height(24.dp))

        // ⭐ Навыки
        if (vacancy.skills.isNotEmpty()) {
            Text(
                text = "Ключевые навыки",
                style = MaterialTheme.typography.titleMedium, // Medium/22
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(Modifier.height(8.dp))
            vacancy.skills.forEach {
                Text(
                    text = "• $it",
                    style = MaterialTheme.typography.bodyMedium, // Regular/16
                    color = MaterialTheme.colorScheme.onBackground
                )
                Spacer(Modifier.height(4.dp))
            }
            Spacer(Modifier.height(24.dp))
        }

        // 📞 Контакты
        vacancy.contacts?.let { contacts ->
            if (contacts.email != null || contacts.phones.isNotEmpty()) {
                Text(
                    text = "Контакты",
                    style = MaterialTheme.typography.titleMedium, // Medium/22
                    color = MaterialTheme.colorScheme.onBackground
                )
                Spacer(Modifier.height(8.dp))
                ContactsBlock(
                    contacts = contacts,
                    onEmailClick = onEmailClick,
                    onPhoneClick = onPhoneClick
                )
            }
        }

        Spacer(Modifier.height(32.dp))
    }
}

@Composable
fun CompanyCard(vacancy: VacancyDetails) {
    Row(
        Modifier
            .fillMaxWidth()
            .background(CompanyCardBackgroundColor, RoundedCornerShape(16.dp))
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(vacancy.logoUrl)
                .crossfade(true)
                .build(),
            placeholder = painterResource(R.drawable.ic_company_placeholder),
            error = painterResource(R.drawable.ic_company_placeholder),
            contentDescription = vacancy.companyName,
            modifier = Modifier.size(48.dp)
        )

        Spacer(Modifier.width(12.dp))

        Column {
            Text(
                text = vacancy.companyName,
                style = MaterialTheme.typography.titleMedium, // Medium/22
                color = TextColorLight
            )
            (vacancy.address ?: vacancy.region)?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.bodyMedium, // Regular/16
                    color = TextColorLight
                )
            }
        }
    }
}

@Composable
fun DescriptionBlock(text: String) {
    text.split("\n").forEach { rawLine ->
        val line = rawLine.trim()
        if (line.isEmpty()) {
            Spacer(Modifier.height(4.dp))
        } else {
            Row {
                Text(
                    text = "• ",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    text = line
                        .removePrefix("•")
                        .removePrefix("-")
                        .trimStart(),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }

            Spacer(Modifier.height(4.dp))
        }
    }
}

@Composable
fun ContactsBlock(
    contacts: VacancyContacts,
    onEmailClick: (String) -> Unit,
    onPhoneClick: (String) -> Unit
) {
    contacts.email?.let {
        Text(
            text = it,
            style = MaterialTheme.typography.bodyMedium, // Regular/16
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.clickable { onEmailClick(it) }
        )
        Spacer(Modifier.height(4.dp))
    }

    contacts.phones.forEach { phone ->
        Text(
            text = phone,
            style = MaterialTheme.typography.bodyMedium, // Regular/16
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.clickable { onPhoneClick(phone) }
        )
        Spacer(Modifier.height(4.dp))
    }

    contacts.comment?.let {
        Spacer(Modifier.height(8.dp))
        Text(
            text = it,
            style = MaterialTheme.typography.bodyMedium, // Regular/16
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

fun shareVacancy(context: Context, url: String) {
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT, url)
    }
    context.startActivity(Intent.createChooser(intent, "Поделиться вакансией"))
}

@SuppressLint("UseKtx")
fun openEmail(context: Context, email: String) {
    val intent = Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:$email"))
    context.startActivity(intent)
}

@SuppressLint("UseKtx")
fun openPhone(context: Context, phone: String) {
    val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phone"))
    context.startActivity(intent)
}
