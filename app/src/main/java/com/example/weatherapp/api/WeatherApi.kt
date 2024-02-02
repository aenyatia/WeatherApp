package com.example.weatherapp.api

import com.example.weatherapp.R
import com.example.weatherapp.models.Forecast
import com.example.weatherapp.models.Weather
import com.squareup.moshi.Json
import retrofit2.http.GET
import retrofit2.http.Query

interface IWeatherApi {
    @GET("data/2.5/forecast?appid=4f1377e351bdc89084238bffc48c599f")
    suspend fun getWeather(
        @Query("q") city: String,
        @Query("units") units: String,
        @Query("lang") lang: String
    ): WeatherApiResponse
}

data class WeatherApiResponse(
    @field:Json(name = "cod") val cod: String,
    @field:Json(name = "message") val message: Int,
    @field:Json(name = "city") val city: CityResponse,
    @field:Json(name = "list") val list: List<ListResponse>,
) {
    data class CityResponse(
        @field:Json(name = "name") val name: String,
        @field:Json(name = "sunrise") val sunrise: Int,
        @field:Json(name = "sunset") val sunset: Int,
        @field:Json(name = "population") val population: Int,
    )

    data class ListResponse(
        @field:Json(name = "dt") val dt: Int,
        @field:Json(name = "dt_txt") val dtTxt: String,
        @field:Json(name = "main") val main: MainResponse,
        @field:Json(name = "weather") val weather: List<WeatherResponse>,
        @field:Json(name = "wind") val wind: WindResponse
    ) {
        data class MainResponse(
            @field:Json(name = "temp") val temp: Double,
            @field:Json(name = "feels_like") val feelsLike: Double,
            @field:Json(name = "temp_min") val tempMin: Double,
            @field:Json(name = "temp_max") val tempMax: Double,
            @field:Json(name = "pressure") val pressure: Int,
            @field:Json(name = "humidity") val humidity: Int
        )

        data class WeatherResponse(
            @field:Json(name = "main") val main: String,
            @field:Json(name = "description") val description: String,
            @field:Json(name = "icon") val icon: String
        )

        data class WindResponse(
            @field:Json(name = "speed") val speed: Double
        )
    }
}

fun WeatherApiResponse.toWeather(): Weather = Weather(
    cityName = city.name,
    forecasts = list.map {
        it.toForecast()
    },
    population = city.population,
    sunrise = city.sunrise,
    sunset = city.sunset
)

fun WeatherApiResponse.ListResponse.toForecast(): Forecast = Forecast(
    weather = weather.first().main,
    weatherDescription = weather.first().description,
    icon = weather.first().icon,
    currentTemp = main.temp,
    feelsTemp = main.feelsLike,
    minTemp = main.tempMin,
    maxTemp = main.tempMax,
    pressure = main.pressure,
    windSpeed = wind.speed,
    humidity = main.humidity,
    time = dtTxt,
    date = dt
)

fun getIconId(icon: String?): Int {
    return when (icon) {
        "01d" -> R.drawable._01d2x
        "02d" -> R.drawable._02d2x
        "03d" -> R.drawable._03d2x
        "04d" -> R.drawable._04d2x
        "09d" -> R.drawable._09d2x
        "10d" -> R.drawable._10d2x
        "11d" -> R.drawable._11d2x
        "13d" -> R.drawable._13d2x
        "50d" -> R.drawable._50d2x
        "01n" -> R.drawable._01n2x
        "02n" -> R.drawable._02n2x
        "03n" -> R.drawable._03n2x
        "04n" -> R.drawable._04n2x
        "09n" -> R.drawable._09n2x
        "10n" -> R.drawable._10n2x
        "11n" -> R.drawable._11n2x
        "13n" -> R.drawable._13n2x
        "50n" -> R.drawable._50n2x
        else -> R.drawable._01d2x
    }
}