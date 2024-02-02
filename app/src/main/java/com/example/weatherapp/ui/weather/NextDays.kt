package com.example.weatherapp.ui.weather

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.weatherapp.api.getIconId
import com.example.weatherapp.ui.WeatherViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun DailyWeatherPage(viewModel: WeatherViewModel) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    val dateFormatter = SimpleDateFormat("EEEE", Locale.ENGLISH)
    val timeFormatter = SimpleDateFormat("HH:mm", Locale.ENGLISH)

    LazyColumn(
        modifier = Modifier
            .background(Color.LightGray)
    ) {
        val forecasts = state.weather?.forecasts ?: listOf()

        items(forecasts) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(24.dp, 24.dp, 24.dp, 0.dp)
                    .fillMaxWidth()
                    .background(Color(0xFFDDDDDD))
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                ) {
                    Text(
                        text = timeFormatter.format(Date(it.date * 1000L)),
                        fontSize = 30.sp
                    )
                    Text(
                        text = dateFormatter.format(Date(it.date * 1000L)),
                        fontSize = 20.sp
                    )
                }
                Text(
                    text = it.currentTemp.toInt().toString() + "°",
                    fontSize = 30.sp,
                    textAlign = TextAlign.Center,
                )
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxSize(0.7f)
                ) {
                    Image(
                        painter = painterResource(id = getIconId(it.icon)),
                        contentDescription = null,
                        modifier = Modifier.size(80.dp, 80.dp)
                    )
                    Text(
                        text = it.weatherDescription,
                        textAlign = TextAlign.Center,
                    )
                }
            }
        }
    }
}