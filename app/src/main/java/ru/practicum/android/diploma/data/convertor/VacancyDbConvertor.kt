package ru.practicum.android.diploma.data.convertor

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ru.practicum.android.diploma.data.db.VacancyEntity
import ru.practicum.android.diploma.domain.models.VacancyContacts
import ru.practicum.android.diploma.domain.models.VacancyDetails

class VacancyDbConvertor {

    private val gson = Gson()

    fun map(vacancy: VacancyDetails): VacancyEntity {
        return VacancyEntity(
            vacancy.id,
            vacancy.title,
            vacancy.description,
            vacancy.companyName,
            vacancy.logoUrl,
            vacancy.salaryFrom,
            vacancy.salaryTo,
            vacancy.currency,
            vacancy.address,
            vacancy.region,
            vacancy.experience,
            vacancy.schedule,
            vacancy.employment,
            gson.toJson(vacancy.skills),
            gson.toJson(vacancy.contacts),
            vacancy.vacancyUrl

        )
    }

    fun map(vacancy: VacancyEntity): VacancyDetails {
        return VacancyDetails(
            vacancy.id,
            vacancy.title,
            vacancy.description,
            vacancy.companyName,
            vacancy.logoUrl,
            vacancy.salaryFrom,
            vacancy.salaryTo,
            vacancy.currency,
            vacancy.address,
            vacancy.region,
            vacancy.experience,
            vacancy.schedule,
            vacancy.employment,
            gson.fromJson(vacancy.skills, object : TypeToken<MutableList<String>>() {}.type),
            gson.fromJson(vacancy.contacts, object : TypeToken<MutableList<VacancyContacts>>() {}.type),
            vacancy.vacancyUrl
        )
    }
}
