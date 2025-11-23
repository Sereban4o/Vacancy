package ru.practicum.android.diploma.di

import androidx.room.Room
import org.koin.dsl.module
import ru.practicum.android.diploma.data.db.AppDatabase
import ru.practicum.android.diploma.data.db.VacancyDao

val databaseModule = module {

    single<AppDatabase> {
        Room.databaseBuilder(
            context = get(),
            klass = AppDatabase::class.java,
            name = "vacancy-db"
        ).build()
    }

    single<VacancyDao> {
        get<AppDatabase>().vacancyDao()
    }
}
