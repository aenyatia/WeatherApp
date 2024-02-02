package com.example.weatherapp

import android.app.Application
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.weatherapp.api.IWeatherApi
import com.example.weatherapp.repositories.IWeatherRepository
import com.example.weatherapp.repositories.WeatherRepository
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create

class WeatherApp : Application() {
    companion object {
        lateinit var appModule: IAppModule
    }

    override fun onCreate() {
        super.onCreate()
        appModule = AppModule()
    }
}

interface IAppModule {
    val weatherApi: IWeatherApi
    val weatherRepository: IWeatherRepository
}

class AppModule : IAppModule {

    override val weatherApi: IWeatherApi by lazy {
        Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create())
            .baseUrl("https://api.openweathermap.org/")
            .build()
            .create()
    }
    override val weatherRepository: IWeatherRepository by lazy {
        WeatherRepository(weatherApi)
    }
}

sealed class Resource<T>(val data: T? = null, val message: String? = null) {
    class Success<T>(data: T?) : Resource<T>(data)
    class Error<T>(message: String, data: T? = null) : Resource<T>(data, message)
}

@Composable
fun rememberWindowInfo(): WindowInfo {
    val configuration = LocalConfiguration.current

    return WindowInfo(
        screenWidthInfo = when {
            configuration.screenWidthDp < 600 -> WindowInfo.WindowType.Compact
            configuration.screenWidthDp < 840 -> WindowInfo.WindowType.Medium
            else -> WindowInfo.WindowType.Expanded
        },
        screenHeightInfo = when {
            configuration.screenHeightDp < 480 -> WindowInfo.WindowType.Compact
            configuration.screenHeightDp < 900 -> WindowInfo.WindowType.Medium
            else -> WindowInfo.WindowType.Expanded
        },
        screenWidth = configuration.screenWidthDp.dp,
        screenHeight = configuration.screenHeightDp.dp
    )
}

data class WindowInfo(
    val screenWidthInfo: WindowType,
    val screenHeightInfo: WindowType,
    val screenWidth: Dp,
    val screenHeight: Dp
) {
    sealed class WindowType {
        data object Compact : WindowType()
        data object Medium : WindowType()
        data object Expanded : WindowType()
    }
}