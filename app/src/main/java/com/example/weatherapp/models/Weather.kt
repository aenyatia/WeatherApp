package com.example.weatherapp.models

import kotlinx.serialization.Serializable

@Serializable
data class Weather(
    val cityName: String,
    val forecasts: List<Forecast>? = null,
    val sunrise: Int,
    val sunset: Int,
    val population: Int
)

@Serializable
data class Forecast(
    val weather: String,
    val weatherDescription: String,
    val icon: String,
    val currentTemp: Double,
    val feelsTemp: Double,
    val minTemp: Double,
    val maxTemp: Double,
    val windSpeed: Double,
    val pressure: Int,
    val humidity: Int,
    val time: String,
    val date: Int
)