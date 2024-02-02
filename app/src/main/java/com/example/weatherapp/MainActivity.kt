package com.example.weatherapp

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.weatherapp.ui.WeatherState
import com.example.weatherapp.ui.WeatherViewModel
import com.example.weatherapp.ui.theme.WeatherAppTheme
import com.example.weatherapp.ui.viewModelFactory
import com.example.weatherapp.ui.weather.CurrentWeatherPage
import com.example.weatherapp.ui.weather.DailyWeatherPage
import com.example.weatherapp.ui.weather.DetailsWeatherPage
import kotlinx.serialization.json.Json

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherAppTheme {
                App()
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun App() {
    val context = LocalContext.current

    val pagerState = rememberPagerState { 3 }
    val sharedPreferences = context.getSharedPreferences("weatherApp", Context.MODE_PRIVATE)

    val viewModel = viewModel<WeatherViewModel>(factory = viewModelFactory {
        WeatherViewModel(WeatherApp.appModule.weatherRepository, sharedPreferences)
    })

    X(viewModel, pagerState)
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun X(viewModel: WeatherViewModel, pagerState: PagerState) {
    val state by viewModel.state.collectAsState()
    val windowInfo = rememberWindowInfo()

    if (state.isLoading) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
        ) {
            Text(
                text = "Loading...",
                fontSize = 50.sp
            )
        }
    } else if (state.error == "HTTP 404 Not Found") {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
        ) {
            Text(
                text = "Not found",
                fontSize = 50.sp
            )
            Spacer(modifier = Modifier.height(12.dp))
            TextField(
                value = viewModel.city.value,
                onValueChange = viewModel::updateCity
            )
            Spacer(modifier = Modifier.height(12.dp))
            Button(onClick = viewModel::loadWeather) {
                Text(text = "Try again")
            }
        }
    } else if (state.weather == null) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = "Error",
                fontSize = 50.sp
            )
            Text(
                text = "check internet connection",
                fontSize = 24.sp
            )
            Spacer(modifier = Modifier.height(12.dp))
            Button(onClick = viewModel::loadWeather) {
                Text(text = "Try again")
            }
        }
    } else {
        if (windowInfo.screenWidthInfo is WindowInfo.WindowType.Medium) {
            Column {
                Row(Modifier.weight(2f)) {
                    Column(Modifier.weight(1f)) {
                        CurrentWeatherPage(viewModel = viewModel)
                    }

                    Column(Modifier.weight(1f)) {
                        DailyWeatherPage(viewModel = viewModel)
                    }

                }
                Row(Modifier.weight(1f)) {
                    DetailsWeatherPage(viewModel = viewModel)
                }
            }
        } else {
            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .fillMaxSize()
            ) { page ->
                when (page) {
                    0 -> CurrentWeatherPage(viewModel)
                    1 -> DetailsWeatherPage(viewModel)
                    2 -> DailyWeatherPage(viewModel)
                }
            }
        }
    }
}