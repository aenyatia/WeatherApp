@file:OptIn(ExperimentalFoundationApi::class)

package com.example.weatherapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.weatherapp.ui.WeatherViewModel
import com.example.weatherapp.ui.theme.WeatherAppTheme
import com.example.weatherapp.ui.viewModelFactory
import com.example.weatherapp.ui.weather.CurrentWeatherPage
import com.example.weatherapp.ui.weather.DailyWeatherPage
import com.example.weatherapp.ui.weather.DetailsWeatherPage

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

@Composable
fun App() {
    val pagerState = rememberPagerState { 3 }
    val viewModel = viewModel<WeatherViewModel>(factory = viewModelFactory {
        WeatherViewModel(WeatherApp.appModule.weatherRepository)
    })

    HorizontalPager(
        state = pagerState,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Yellow)
    ) { page ->
        when (page) {
            0 -> CurrentWeatherPage(viewModel)
            1 -> DetailsWeatherPage(viewModel)
            2 -> DailyWeatherPage(viewModel)
        }
    }
}