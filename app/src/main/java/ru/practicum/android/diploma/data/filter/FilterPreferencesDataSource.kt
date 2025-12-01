package ru.practicum.android.diploma.data.filter

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.util.Log
import com.google.gson.Gson
import com.google.gson.JsonIOException
import com.google.gson.JsonSyntaxException
import ru.practicum.android.diploma.domain.models.FilterSettings

/**
 * Класс, который реально работает с SharedPreferences.
 * Здесь и происходит запись/чтение JSON-строки.
 */
class FilterPreferencesDataSource(
    private val sharedPreferences: SharedPreferences,
    private val gson: Gson,
) {

    /**
     * Читаем JSON-строку из SharedPreferences и превращаем в FilterSettings.
     * Если ничего нет или формат повреждён — возвращаем null.
     */
    fun readFilterSettings(): FilterSettings? {
        val json = sharedPreferences.getString(KEY_FILTER_SETTINGS, null)
        if (json.isNullOrBlank()) {
            return null
        }

        return try {
            gson.fromJson(json, FilterSettings::class.java)
        } catch (e: JsonSyntaxException) {
            Log.w(TAG, "Failed to parse filter settings JSON, clearing it", e)
            // некорректный / устаревший JSON → считаем, что настроек нет
            null
        } catch (e: JsonIOException) {
            Log.w(TAG, "Failed to read filter settings JSON", e)
            // проблемы чтения → тоже возвращаем "нет настроек"
            null
        }
    }

    /**
     * Принимаем FilterSettings, конвертируем в JSON
     * и сохраняем в SharedPreferences.
     */
    @SuppressLint("UseKtx")
    fun writeFilterSettings(settings: FilterSettings) {
        val json = gson.toJson(settings)
        sharedPreferences.edit()
            .putString(KEY_FILTER_SETTINGS, json)
            .apply()
    }

    /**
     * Полностью сбрасываем сохранённые настройки фильтра.
     */
    @SuppressLint("UseKtx")
    fun clearFilterSettings() {
        sharedPreferences.edit()
            .remove(KEY_FILTER_SETTINGS)
            .apply()
    }

    companion object {
        // Ключ, под которым в SharedPreferences лежит JSON с настройками фильтра
        private const val KEY_FILTER_SETTINGS = "filter_settings_json"
        private const val TAG = "FilterPreferencesDataSource"
    }
}
