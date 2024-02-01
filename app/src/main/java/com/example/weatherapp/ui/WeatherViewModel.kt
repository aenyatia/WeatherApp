package com.example.weatherapp.ui

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.Resource
import com.example.weatherapp.models.Weather
import com.example.weatherapp.repositories.IWeatherRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable

class WeatherViewModel(
    private val repository: IWeatherRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(WeatherState())
    val state = _state.asStateFlow()

    var city = mutableStateOf("Lodz")
        private set

    fun updateCity(value: String) {
        city.value = value
    }

    fun loadWeather() {
        viewModelScope.launch {
            _state.value = _state.value.copy(
                isLoading = true,
                error = null
            )

            delay(3000)

            when (val result = repository.getWeather(city.value)) {
                is Resource.Success -> {
                    _state.value = _state.value.copy(
                        weatherInfo = result.data,
                        isLoading = false,
                        error = null
                    )
                }

                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        weatherInfo = null,
                        isLoading = false,
                        error = result.message
                    )
                }
            }
        }
    }
}

fun <VM : ViewModel> viewModelFactory(initializer: () -> VM): ViewModelProvider.Factory {
    return object : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return initializer() as T
        }
    }
}

@Serializable
data class WeatherState(
    val weatherInfo: Weather? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)