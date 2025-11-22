package ru.practicum.android.diploma.ui.root

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.practicum.android.diploma.BuildConfig
import ru.practicum.android.diploma.R

class RootActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_root)

        // Пример использования access token для HeadHunter API
//        networkRequestExample(accessToken = BuildConfig.API_ACCESS_TOKEN)

        // В будущем здесь будет реальная инициализация сети / DI / репозиториев
        networkRequestExample()
    }

//    private fun networkRequestExample(accessToken: String) {
//        // ...
//    }

    @Suppress("UNUSED_VARIABLE")
    private fun networkRequestExample() {
        val accessToken = BuildConfig.API_ACCESS_TOKEN
        // TODO: использовать accessToken при инициализации клиента / запросе
    }

}
