package com.example.weatherapp.models

data class Weather(
    val cityName: String,
    val forecasts: List<Forecast>? = null
)

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
    val time: String
)