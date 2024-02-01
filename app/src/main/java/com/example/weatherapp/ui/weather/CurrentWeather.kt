package com.example.weatherapp.ui.weather

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.weatherapp.ui.WeatherViewModel

@Composable
fun CurrentWeatherPage(viewModel: WeatherViewModel) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    Column {
        Text(
            text = "isLoading: " + state.isLoading.toString(),
            fontSize = 36.sp
        )
        Text(
            text = "http code: " + state.weatherInfo?.code.toString(),
            fontSize = 36.sp
        )
        Text(
            text = "errors: " + state.weatherInfo?.message,
            fontSize = 36.sp
        )
        Button(onClick = viewModel::loadWeather) {
            Text(text = "Update")
        }
    }
}