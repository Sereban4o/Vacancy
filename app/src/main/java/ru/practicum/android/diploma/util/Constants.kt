package ru.practicum.android.diploma.util

/**
 * Общие константы приложения.
 *
 * Вынесены сюда, чтобы не было "магических чисел" и значений,
 * размазанных по утилитам.
 */
object Constants {

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
