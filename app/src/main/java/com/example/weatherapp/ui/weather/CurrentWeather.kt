package com.example.weatherapp.ui.weather

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weatherapp.ui.WeatherViewModel

@Composable
fun CurrentWeatherPage(viewModel: WeatherViewModel) {
    val state by viewModel.state.collectAsState()

    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Gray)
    ) {

        // city, time
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Yellow)
        ) {
            Text(
                text = state.weather?.cityName ?: ""
            )
            Text(
                text = state.weather?.forecasts?.first()?.time ?: ""
            )
        }

        // temperature
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Green)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.LightGray)
            ) {
                Text(
                    text = state.weather?.forecasts?.first()?.currentTemp.toString(),
                    fontSize = 120.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .background(Color.Blue)
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = state.weather?.forecasts?.first()?.weather ?: "",
                    fontSize = 24.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .background(Color.Red)
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = state.weather?.forecasts?.first()?.weatherDescription ?: "",
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .background(Color.Red)
                )
            }
        }

        Spacer(modifier = Modifier.height(30.dp))

        // next hours
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Cyan)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "20",
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .background(Color.Magenta)
                )
                Text(text = "12:00")
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "21",
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .background(Color.Magenta)
                )
                Text(text = "13:00")
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "22",
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .background(Color.Magenta)
                )
                Text(text = "14:00")
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "20",
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .background(Color.Magenta)
                )
                Text(text = "15:00")
            }
        }

        Spacer(modifier = Modifier.height(30.dp))

        // pressure, ..., wind
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Yellow)
        ) {
            Text(
                text = "Pressure: " + state.weather?.forecasts?.first()?.pressure
            )
            Text(
                text = "Humidity: " + state.weather?.forecasts?.first()?.humidity
            )
            Text(
                text = "Wind: " + state.weather?.forecasts?.first()?.windSpeed
            )
        }
    }
}