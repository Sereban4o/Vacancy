package ru.practicum.android.diploma.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.practicum.android.diploma.presentation.filter.country.CountryViewModel
import ru.practicum.android.diploma.presentation.favorites.FavoritesViewModel
import ru.practicum.android.diploma.presentation.filter.FilterViewModel
import ru.practicum.android.diploma.presentation.filter.industry.IndustryViewModel
import ru.practicum.android.diploma.presentation.filter.region.RegionViewModel
import ru.practicum.android.diploma.presentation.search.SearchViewModel
import ru.practicum.android.diploma.presentation.vacancydetails.VacancyDetailsViewModel
import ru.practicum.android.diploma.presentation.filter.workplace.WorkPlaceViewModel

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
            get()
        )
    }

    viewModel {
        IndustryViewModel(
            industriesInteractor = get(),
            filterSettingsInteractor = get()
        )
    }

    viewModel {
        WorkPlaceViewModel(
            filterSettingsInteractor = get()
        )
    }

    viewModel {
        CountryViewModel(
            countriesInteractor = get(),
            filterSettingsInteractor = get()
        )
    }

    viewModel {
        RegionViewModel(
            regionsInteractor = get(),
            filterSettingsInteractor = get()
        )
    }

    // 🔹 ViewModel для экрана фильтрации
    viewModel {
        FilterViewModel(
            repository = get()
        )
    }
}
