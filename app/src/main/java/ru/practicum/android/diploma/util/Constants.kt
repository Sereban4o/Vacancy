package ru.practicum.android.diploma.util

/**
 * Общие константы приложения.
 *
 * Вынесены сюда, чтобы не было "магических чисел" и значений,
 * размазанных по утилитам.
 */
object Constants {

    object Network {
        /**
         * Временное значение "по умолчанию": считаем, что сеть есть.
         * Потом здесь же можно заменить на реальную логику/флаг.
         */
        const val DEFAULT_ONLINE: Boolean = true
    }

    object Debounce {
        /**
         * Задержка для debounce-поиска (мс).
         * Используется, например, в экране поиска вакансий.
         */
        const val SEARCH_DELAY_MS: Long = 600L
    }
}
