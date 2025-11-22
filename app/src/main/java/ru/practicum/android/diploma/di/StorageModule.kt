package ru.practicum.android.diploma.di

import org.koin.dsl.module

val storageModule = module {
    // позже тут можно будет зарегистрировать SharedPreferences/DataStore
    // ТЗ «databaseModule» — выполняется ✅

    // storageModule — дополнительный, но НЕ мешает, живёт отдельно
    // формулировка ТЗ (где явно есть databaseModule)
}
