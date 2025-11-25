package ru.practicum.android.diploma.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.practicum.android.diploma.presentation.search.SearchViewModel
import ru.practicum.android.diploma.presentation.vacancydetails.VacancyDetailsViewModel

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
