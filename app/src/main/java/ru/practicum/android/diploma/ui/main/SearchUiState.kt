package ru.practicum.android.diploma.ui.main

/**
 * UI-состояние экрана поиска вакансий.
 *
 * @param query      текущий текст поисковой строки
 * @param isLoading  флаг загрузки (для первичного лоадера / блокировки экрана)
 * @param errorType  тип ошибки (нет интернета / общая / без ошибки)
 * @param totalFound общее количество найденных вакансий (приходит из backend)
 * @param isInitial  true, пока пользователь ещё ни разу не запускал поиск
 *
 * ⚡️ Важно:
 * - вакансии больше НЕ храним — ими занимается Paging 3
 *   (LazyPagingItems<Vacancy> в UI).
 */
data class SearchUiState(
    val query: String = "",
    val isLoading: Boolean = false,
    val errorType: SearchErrorType = SearchErrorType.NONE,
    val totalFound: Int = 0,
    val isInitial: Boolean = true
)
