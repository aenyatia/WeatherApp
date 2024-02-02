package com.example.weatherapp.ui.weather

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weatherapp.R
import com.example.weatherapp.api.getIconId
import com.example.weatherapp.ui.WeatherViewModel
import java.time.format.DateTimeFormatter

@Composable
fun CurrentWeatherPage(viewModel: WeatherViewModel) {
    val state by viewModel.state.collectAsState()

    val dateFormatter = DateTimeFormatter.ofPattern("dd MM yyyy")
    val timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss")

    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.LightGray)
    ) {

        // city, time
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            TextField(
                value = viewModel.city.value,
                onValueChange = viewModel::updateCity,
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth(0.3f)
            )
            Text(
                text = "Last update: " + viewModel.lastUpdate.value.format(timeFormatter),
            )
            Image(
                painter = painterResource(id = R.drawable.reload),
                contentDescription = null,
                modifier = Modifier
                    .size(40.dp, 40.dp)
                    .fillMaxSize()
                    .padding(5.dp)
                    .clickable(
                        onClick = {
                            viewModel.loadWeather()
                        }
                    )
            )
        }

        // temperature
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = state.weather?.cityName ?: "",
                fontSize = 50.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Text(
                text = " " + state.weather?.forecasts?.first()?.currentTemp?.toInt()
                    .toString() + "°",
                fontSize = 120.sp,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Image(
                painter = painterResource(id = getIconId(state.weather?.forecasts?.first()?.icon)),
                contentDescription = "",
                modifier = Modifier.size(200.dp, 200.dp)
            )

            Text(
                text = state.weather?.forecasts?.first()?.weather ?: "",
                fontSize = 42.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
            )

            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = state.weather?.forecasts?.first()?.weatherDescription ?: "",
                fontSize = 24.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
            )
        }

        Spacer(modifier = Modifier.height(30.dp))

        // pressure, humidity, wind
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Gray)
                .height(50.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxHeight()
            ) {
                Image(
                    painter = painterResource(id = R.drawable.pressure),
                    contentDescription = null,
                    modifier = Modifier
                        .size(30.dp, 30.dp)
                )
                Text(
                    text = " " + state.weather?.forecasts?.first()?.pressure.toString() + " hPa"
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxHeight()
            ) {
                Image(
                    painter = painterResource(id = R.drawable.humidity),
                    contentDescription = null,
                    modifier = Modifier
                        .size(30.dp, 30.dp)
                )
                Text(
                    text = " " + state.weather?.forecasts?.first()?.humidity.toString() + " %"
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxHeight()
            ) {
                Image(
                    painter = painterResource(id = R.drawable.windy),
                    contentDescription = null,
                    modifier = Modifier
                        .size(30.dp, 30.dp)
                )
                Text(
                    text = " " + state.weather?.forecasts?.first()?.windSpeed.toString() + " km/h"
                )
            }
        }
    }
}