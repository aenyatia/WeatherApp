package com.example.weatherapp.repositories

import com.example.weatherapp.Resource
import com.example.weatherapp.api.IWeatherApi
import com.example.weatherapp.api.toWeather
import com.example.weatherapp.models.Weather

interface IWeatherRepository {
    suspend fun getWeather(city: String): Resource<Weather>
}

class WeatherRepository(private val api: IWeatherApi) : IWeatherRepository {
    override suspend fun getWeather(city: String): Resource<Weather> {
        return try {
            Resource.Success(
                data = api.getWeather(city, "metric", "en").toWeather()
            )
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message ?: "An unknown error occurred")
        }
    }
}