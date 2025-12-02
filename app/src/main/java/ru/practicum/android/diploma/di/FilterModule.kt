import org.koin.dsl.module
import org.koin.android.ext.koin.androidContext
import ru.practicum.android.diploma.data.filter.FilterPreferencesDataSource
import ru.practicum.android.diploma.data.filter.FilterSettingsRepositoryImpl
import ru.practicum.android.diploma.domain.repository.FilterSettingsRepository
import ru.practicum.android.diploma.domain.interactors.FilterSettingsInteractor

val filterModule = module {

    // SharedPreferences (если ещё где-то не создаёшь отдельно)
    single {
        androidContext().getSharedPreferences(
            "filter_prefs",
            android.content.Context.MODE_PRIVATE
        )
    }

    // DataSource над SharedPreferences
    single {
        FilterPreferencesDataSource(
            sharedPreferences = get()
        )
    }

    // Repository
    single<FilterSettingsRepository> {
        FilterSettingsRepositoryImpl(
            dataSource  = get()
        )
    }

    // Interactor
    single {
        FilterSettingsInteractor(
            repository = get()
        )
    }
}
