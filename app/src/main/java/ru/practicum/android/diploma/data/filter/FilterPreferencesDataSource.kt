package ru.practicum.android.diploma.data.filter

import android.content.SharedPreferences
import android.util.Log
import androidx.core.content.edit
import kotlinx.serialization.SerializationException
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import ru.practicum.android.diploma.domain.models.FilterSettings

/**
 * SharedPreferences-слой, но используем kotlinx.serialization вместо Gson.
 */
class FilterPreferencesDataSource(
    private val sharedPreferences: SharedPreferences,
) {
    private val json = Json {
        prettyPrint = false
        ignoreUnknownKeys = true // важно при обновлении версии
        encodeDefaults = true
    }

    fun readFilterSettings(): FilterSettings? {
        val stored = sharedPreferences.getString(KEY_FILTER_SETTINGS, null)
            ?: return null

        return try {
            json.decodeFromString<FilterSettings>(stored)
        } catch (e: SerializationException) {
            // JSON есть, но структура не совпадает с FilterSettings
            Log.w(TAG, "Failed to parse FilterSettings from JSON, clearing saved filter", e)
            null
        } catch (e: IllegalArgumentException) {
            // В строке вообще невалидный JSON
            Log.w(TAG, "Invalid JSON stored for FilterSettings, clearing saved filter", e)
            null
        }
    }

    fun writeFilterSettings(settings: FilterSettings) {
        val encoded = json.encodeToString(settings)
        this.sharedPreferences.edit {
            putString(KEY_FILTER_SETTINGS, encoded)
        }
    }

    fun clearFilterSettings() {
        sharedPreferences.edit {
            remove(KEY_FILTER_SETTINGS)
        }
    }

    companion object {
        private const val KEY_FILTER_SETTINGS = "filter_settings_json"
        private const val TAG = "FilterPreferences"
    }
}
