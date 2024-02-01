package com.example.weatherapp

import android.app.Application
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