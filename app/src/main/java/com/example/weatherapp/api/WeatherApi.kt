package com.example.weatherapp.api

import com.example.weatherapp.models.Weather
import retrofit2.http.GET
import retrofit2.http.Query

interface IWeatherApi {
    @GET("data/2.5/forecast?appid=4f1377e351bdc89084238bffc48c599f")
    suspend fun getWeather(
        @Query("q") city: String,
        @Query("units") units: String,
        @Query("lang") lang: String
    ): Weather
}