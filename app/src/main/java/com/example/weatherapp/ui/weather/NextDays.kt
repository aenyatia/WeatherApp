package com.example.weatherapp.ui.weather

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.sp
import com.example.weatherapp.ui.WeatherViewModel

@Composable
fun DailyWeatherPage(viewModel: WeatherViewModel) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Next Days",
            fontSize = 48.sp
        )
    }
}