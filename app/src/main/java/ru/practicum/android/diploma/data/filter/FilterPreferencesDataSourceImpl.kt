package ru.practicum.android.diploma.data.filter

import android.content.SharedPreferences
import android.util.Log
import androidx.core.content.edit
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.SerializationException
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import ru.practicum.android.diploma.domain.models.FilterSettings

/**
 * SharedPreferences-реализация хранения FilterSettings c kotlinx.serialization.
 */
class FilterPreferencesDataSourceImpl(
    private val sharedPreferences: SharedPreferences,
) : FilterPreferencesDataSource {

    private val json = Json {
        prettyPrint = false
        ignoreUnknownKeys = true
        encodeDefaults = true
    }

    override suspend fun readFilterSettings(): FilterSettings? =
        withContext(Dispatchers.IO) {
            val stored = sharedPreferences.getString(KEY_FILTER_SETTINGS, null)
                ?: return@withContext null

            try {
                json.decodeFromString<FilterSettings>(stored)
            } catch (e: SerializationException) {
                Log.w(TAG, "Failed to parse FilterSettings from JSON", e)
                null
            } catch (e: IllegalArgumentException) {
                Log.w(TAG, "Invalid JSON stored for FilterSettings", e)
                null
            }
        }

    override suspend fun writeFilterSettings(settings: FilterSettings) =
        withContext(Dispatchers.IO) {
            val encoded = json.encodeToString(settings)
            sharedPreferences.edit {
                putString(KEY_FILTER_SETTINGS, encoded)
            }
        }

    override suspend fun clearFilterSettings() =
        withContext(Dispatchers.IO) {
            sharedPreferences.edit {
                remove(KEY_FILTER_SETTINGS)
            }
        }

    companion object {
        private const val KEY_FILTER_SETTINGS = "filter_settings_json"
        private const val TAG = "FilterPreferences"
    }
}
