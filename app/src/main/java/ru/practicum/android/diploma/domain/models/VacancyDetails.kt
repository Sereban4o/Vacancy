package ru.practicum.android.diploma.domain.models

data class VacancyDetails(
    val id: String,
    val title: String,
    val description: String,
    val companyName: String,
    val logoUrl: String?,
    val salaryFrom: Int?,
    val salaryTo: Int?,
    val currency: String?,
    val address: String?, // fullAddress или null
    val region: String?, // area.name
    val experience: String?, // Experience.name
    val schedule: String?, // Schedule.name
    val employment: String?, // Employment.name
    val skills: List<String>, // пустой список, если с сервера пришёл null
    val contacts: VacancyContacts?, // null, если нет contacts
    val vacancyUrl: String // url
)

data class VacancyContacts(
    val email: String?,
    val phones: List<String>, // пустой список, если нет телефонов
    val comment: String? // Contacts.name – комментарий к контактам/телефону
)
