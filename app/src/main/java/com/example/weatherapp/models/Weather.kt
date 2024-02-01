package com.example.weatherapp.models

import com.squareup.moshi.Json
import kotlinx.serialization.Serializable

@Serializable
data class Weather(
    @field:Json(name = "cod")
    val code: Int = 0,

    @field:Json(name = "message")
    val message: String = ""
)