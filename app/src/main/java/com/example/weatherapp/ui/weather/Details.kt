package com.example.weatherapp.ui.weather

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.weatherapp.ui.WeatherViewModel
import java.sql.Time

@Composable
fun DetailsWeatherPage(viewModel: WeatherViewModel) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    val sunrise = Time((state.weather?.sunrise ?: 0) * 1000L).toString()
    val sunset = Time((state.weather?.sunset ?: 0) * 1000L).toString()

    Column(
        horizontalAlignment = Alignment.Start,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.LightGray)
            .padding(24.dp)
    ) {
        Text(
            text = "Details",
            fontSize = 50.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Temp: " + state.weather?.forecasts?.first()?.minTemp.toString() + "°",
            fontSize = 24.sp,
        )
        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Feels like: " + state.weather?.forecasts?.first()?.feelsTemp.toString() + "°",
            fontSize = 24.sp,
        )
        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Min temp: " + state.weather?.forecasts?.first()?.minTemp.toString() + "°",
            fontSize = 24.sp,
        )
        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Max temp: " + state.weather?.forecasts?.first()?.maxTemp.toString() + "°",
            fontSize = 24.sp,
        )
        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Population: " + state.weather?.population.toString(),
            fontSize = 24.sp,
        )
        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Sunrise: $sunrise",
            fontSize = 24.sp,
        )
        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Sunset: $sunset",
            fontSize = 24.sp,
        )
    }
}