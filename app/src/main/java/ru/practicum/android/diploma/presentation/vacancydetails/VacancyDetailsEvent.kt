package ru.practicum.android.diploma.presentation.vacancydetails

import androidx.compose.runtime.Immutable

@Immutable
sealed class VacancyDetailsEvent {
    data class Share(val url: String) : VacancyDetailsEvent()
    data class Call(val phone: String) : VacancyDetailsEvent()
    data class Email(val email: String) : VacancyDetailsEvent()
}
