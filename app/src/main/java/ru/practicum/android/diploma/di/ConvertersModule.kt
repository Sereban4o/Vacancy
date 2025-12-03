package ru.practicum.android.diploma.di

import com.google.gson.Gson
import org.koin.dsl.module
import ru.practicum.android.diploma.data.convertor.VacancyDbConvertor

val convertersModule = module {

    // Один экземпляр Gson на всё приложение
    single { Gson() }

    // VacancyDbConvertor теперь получает Gson через DI
    single { VacancyDbConvertor(get()) }
}
