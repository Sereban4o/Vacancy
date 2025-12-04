package ru.practicum.android.diploma.data.convertor

import android.util.Log
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken
import ru.practicum.android.diploma.data.db.VacancyEntity
import ru.practicum.android.diploma.domain.models.VacancyContacts
import ru.practicum.android.diploma.domain.models.VacancyDetails

class VacancyDbConvertor(
    private val gson: Gson // убран gson
) {

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
            parseContacts(vacancy.contacts),
            vacancy.vacancyUrl
        )
    }

    private fun parseContacts(json: String?): VacancyContacts? {
        if (json.isNullOrBlank() || json == "null") {
            return null
        }

        return try {
            gson.fromJson(json, VacancyContacts::class.java)
        } catch (e: JsonSyntaxException) {
            Log.e(TAG, "Error parsing contacts json: $json", e)
            null
        }
    }

    companion object {
        private const val TAG = "VacancyDbConvertor"
    }
}
