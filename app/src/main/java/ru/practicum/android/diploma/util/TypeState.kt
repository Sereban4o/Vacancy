package ru.practicum.android.diploma.util

/* SearchVacancy - Изображение на главном экране до начала поиска,
* NoInternet - Нет интернета,
* NoDataVacancy - Вакансии не найдены,
* NoRegion - Регион не найден,
* NoDataRegion - Не удалось получить список,
* NoVacancy - Вакансия не найдена или удалена,
* ServerError - Ошибка сервера на главной странице,
* ServerErrorVacancy - Ошибка сервера в деталях вакансии,
* EmptyList - Список пуст */
enum class TypeState {
    SearchVacancy,
    NoInternet,
    NoDataVacancy,
    NoRegion,
    NoDataRegion,
    NoVacancy,
    ServerError,
    ServerErrorVacancy,
    EmptyList,
    NoIndustry,
}
