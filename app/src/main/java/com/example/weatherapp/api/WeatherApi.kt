package com.example.weatherapp.api

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
    }
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
    time = dtTxt
)