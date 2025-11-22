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
         * В ТЗ для Epic 1 — 2000 мс, сюда можно будет обновить под него.
         */
        const val SEARCH_DELAY_MS: Long = 2_000L
    }

    object Navigation {
        /**
         * Route экрана деталей вакансии.
         * Используется в NavGraph и хелперах навигации.
         */
        const val VACANCY_DETAILS_ROUTE: String = "VacancyDetails"

        /**
         * Имя аргумента для ID вакансии в навигации.
         * Route: VacancyDetails/{vacancyId}
         */
        const val ARG_VACANCY_ID: String = "vacancyId"
    }
}
