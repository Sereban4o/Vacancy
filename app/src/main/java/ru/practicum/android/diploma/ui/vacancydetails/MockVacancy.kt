package ru.practicum.android.diploma.ui.vacancydetails

import ru.practicum.android.diploma.domain.models.VacancyContacts
import ru.practicum.android.diploma.domain.models.VacancyDetails

private const val MOCK_APPLE_LOGO_URL =
    "https://upload.wikimedia.org/wikipedia/commons/thumb/f/fa/" +
        "Apple_logo_black.svg/1200px-Apple_logo_black.svg.png"

private const val MOCK_APPLE_VACANCY_URL =
    "https://cek6h0n4ffe7ur.cluster-czz5s0kz4scl.eu-west-1." +
        "rds.amazonaws.com/vacancies/0001b24b-da81-48cd-a420-91e3fbfc5ef0"

val mockVacancyDetails = VacancyDetails(
    id = "0001b24b-da81-48cd-a420-91e3fbfc5ef0",
    title = "Backend Developer в Apple",
    companyName = "Apple",
    logoUrl = MOCK_APPLE_LOGO_URL,

    salaryFrom = null,
    salaryTo = null,
    currency = "USD",

    address = "Барнаул, Ленина, 16",
    region = "Лопатино (Пензенская область)",

    experience = "Нет опыта",
    schedule = "Полный день",
    employment = "Полная занятость",

    description = """
        О нас

        Стартап. Создаём инновационный маркетплейс в сфере развлечений и туризма...

        Задача

        Активное развитие маркетплейса, отвечая за серверную логику, API, базу данных и общую архитектуру.

        Для нас крайне важно

        - Производительность backend (оптимизация запросов, кэширование);
        - Безопасность (защита от уязвимостей, аутентификация и авторизация);
        - Масштабируемость (архитектура, позволяющая выдерживать растущую нагрузку);
        - Поддержка Frontend (удобный и эффективный API).
    """.trimIndent(),

    skills = listOf("NoSQL", "Python", "Ruby", "TypeScript", "PHP"),

    contacts = VacancyContacts(
        email = null, // в JSON пустая строка — считаем, что нет email
        phones = listOf(
            "+7 (999) 234-56-78",
            "+7 (999) 876-54-32"
        ),
        comment = null
    ),
    vacancyUrl = MOCK_APPLE_VACANCY_URL
)
