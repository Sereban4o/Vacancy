package ru.practicum.android.diploma.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.practicum.android.diploma.presentation.favorites.FavoritesViewModel
import ru.practicum.android.diploma.presentation.search.SearchViewModel
import ru.practicum.android.diploma.presentation.vacancydetails.VacancyDetailsViewModel

val viewModelModule = module {

    viewModel {
        SearchViewModel(
            searchVacanciesInteractor = get(),
            filterSettingsInteractor = get()
        )
    }

    // ViewModel БЕЗ параметров, Koin сам даст SavedStateHandle
    viewModel {
        VacancyDetailsViewModel(
            savedStateHandle = get(),
            interactor = get(),
            favoritesInteractor = get()
        )
    }

    viewModel {
        FavoritesViewModel(
            get(),
            get()
        )
    }
}
