package ru.practicum.android.diploma.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.interactors.FilterSettingsInteractor
import ru.practicum.android.diploma.domain.interactors.SearchVacanciesInteractor
import ru.practicum.android.diploma.domain.models.SearchFilters
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.domain.models.isActiveForSearch
import ru.practicum.android.diploma.domain.models.toSearchFilters
import ru.practicum.android.diploma.ui.main.SearchErrorType
import ru.practicum.android.diploma.ui.main.SearchUiState
import java.io.IOException

class SearchViewModel(
    private val searchVacanciesInteractor: SearchVacanciesInteractor,
    private val filterSettingsInteractor: FilterSettingsInteractor,
) : ViewModel() {

    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState: StateFlow<SearchUiState> = _uiState.asStateFlow()

    // сырой текст запроса
    private val searchQueryFlow = MutableStateFlow("")

    // 🔹 отдельный flow с ТЕКУЩИМИ фильтрами (то, чего нам не хватало)
    private val filtersFlow = MutableStateFlow(
        SearchFilters(
            regionId = null,
            industryId = null,
            salaryFrom = null,
            onlyWithSalary = false
        )
    )

    init {
        // при создании VM грузим фильтры из репозитория
        viewModelScope.launch {
            val filterSettings = filterSettingsInteractor.getFilterSettings()
            val searchFilters = filterSettings.toSearchFilters()

            filtersFlow.value = searchFilters

            _uiState.update { current ->
                current.copy(
                    hasActiveFilter = filterSettings.isActiveForSearch()
                )
            }
        }
    }

    /**
     * Обновление состояния фильтра при возврате с экрана фильтров.
     * (вызывается из MainScreen через searchViewModel.refreshFilterState())
     */
    fun refreshFilterState() {
        viewModelScope.launch {
            val filterSettings = filterSettingsInteractor.getFilterSettings()
            val searchFilters = filterSettings.toSearchFilters()

            filtersFlow.value = searchFilters
            _uiState.update { current ->
                current.copy(
                    hasActiveFilter = filterSettings.isActiveForSearch()
                )
            }
        }
    }

    /**
     * Основной поток PagingData<Vacancy>.
     *
     * 🔹 ВАЖНО: комбинируем:
     *   - текст запроса (searchQueryFlow)
     *   - фильтры (filtersFlow)
     *
     * И при изменении ЛЮБОГО из них стартует новый поиск.
     */
    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    val pagingResultDataFlow: Flow<PagingData<Vacancy>> =
        searchQueryFlow
            .debounce(SEARCH_DELAY_MS)
            .distinctUntilChanged()
            .combine(filtersFlow) { query, filters ->
                query to filters
            }
            .flatMapLatest { (query, filters) ->
                if (query.isBlank()) {
                    // пустой запрос → пустой список
                    _uiState.update { current ->
                        current.copy(
                            isLoading = false,
                            errorType = SearchErrorType.NONE,
                            totalFound = 0,
                            isInitial = true
                        )
                    }
                    flowOf(PagingData.empty())
                } else {
                    _uiState.update { current ->
                        current.copy(
                            isLoading = true,
                            errorType = SearchErrorType.NONE,
                            isInitial = false,
                            totalFound = 0
                        )
                    }

                    searchVacanciesInteractor.searchPaged(
                        query = query,
                        filters = filters,
                        onTotalFound = { total ->
                            _uiState.update { it.copy(totalFound = total) }
                        }
                    )
                }
            }
            .cachedIn(viewModelScope)

    /**
     * Пользователь меняет текст в поиске.
     */
    fun onQueryChanged(newQuery: String) {
        _uiState.update { current ->
            current.copy(
                query = newQuery,
                isInitial = false,
            )
        }
        searchQueryFlow.value = newQuery
    }

    /**
     * View сообщает сюда изменения loadState Paging'а.
     * Здесь мы обновляем isLoading + errorType.
     */
    fun onLoadStateChanged(loadState: CombinedLoadStates) {
        val refresh = loadState.refresh
        _uiState.update { current ->
            when (refresh) {
                is LoadState.Loading -> {
                    current.copy(
                        isLoading = true,
                        errorType = SearchErrorType.NONE
                    )
                }

                is LoadState.NotLoading -> {
                    current.copy(
                        isLoading = false
                    )
                }

                is LoadState.Error -> {
                    val errorType = if (refresh.error is IOException) {
                        SearchErrorType.NETWORK
                    } else {
                        SearchErrorType.GENERAL
                    }
                    current.copy(
                        isLoading = false,
                        errorType = errorType
                    )
                }
            }
        }
    }

    /**
     * Повторить поиск при ошибке (если понадобится).
     */
    fun onRetry() {
        val currentQuery = _uiState.value.query
        if (currentQuery.isNotBlank()) {
            searchQueryFlow.value = currentQuery
        }
    }

    /**
     * Вызов с экрана фильтра при нажатии "Применить".
     *
     *   - НЕ трогаем searchQueryFlow напрямую,
     *   - просто обновляем filtersFlow (через refreshFilterState),
     *   - а pagingResultDataFlow сам это подхватит через combine().
     */
    fun onFiltersApplied() {
        viewModelScope.launch {
            val filterSettings = filterSettingsInteractor.getFilterSettings()
            val searchFilters = filterSettings.toSearchFilters()

            // 1. подсветка иконки
            _uiState.update { current ->
                current.copy(
                    hasActiveFilter = filterSettings.isActiveForSearch()
                )
            }

            // 2. обновляем filtersFlow → это вызовет новый поиск, если query не пустой
            filtersFlow.value = searchFilters
        }
    }

    companion object {
        private const val SEARCH_DELAY_MS: Long = 2_000L
    }
}
