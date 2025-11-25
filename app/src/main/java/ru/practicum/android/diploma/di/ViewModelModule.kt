package ru.practicum.android.diploma.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.practicum.android.diploma.presentation.searchScreen.SearchViewModel
import ru.practicum.android.diploma.presentation.vacancyDetailsScreen.VacancyDetailsViewModel

val viewModelModule = module {

    viewModel {
        SearchViewModel(
            searchVacanciesInteractor = get()
        )
    }

    // ViewModel с параметром vacancyId
    viewModel { (vacancyId: String) ->
        VacancyDetailsViewModel(
            vacancyId = vacancyId,
            interactor = get()
        )
    }
}
