package com.example.weatherapp.ui

import android.content.SharedPreferences
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.Resource
import com.example.weatherapp.models.Weather
import com.example.weatherapp.repositories.IWeatherRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

class WeatherViewModel(
    private val repository: IWeatherRepository,
    private val preferences: SharedPreferences
) : ViewModel() {

    private val _state = MutableStateFlow(WeatherState())
    val state = _state.asStateFlow()

    var city = mutableStateOf("Warsaw")
        private set

    fun updateCity(value: String) {
        city.value = value
    }

    var lastUpdate = mutableStateOf(LocalDateTime.now())
        private set

    init {
        val lastUpdateJson = preferences.getString("d", null)
        val lastWeaterJson = preferences.getString("w", null)
        if (lastUpdateJson != null && lastWeaterJson != null) {
            _state.value = Json.decodeFromString<WeatherState>(lastWeaterJson)
            lastUpdate.value = LocalDateTime.parse(lastUpdateJson)
        }

        val hourFromLastUpdate = ChronoUnit.HOURS.between(lastUpdate.value, LocalDateTime.now())
        if (hourFromLastUpdate > 3)
            loadWeather()
    }

    fun loadWeather() {
        if (city.value.isBlank()) return

        viewModelScope.launch {
            _state.value = _state.value.copy(
                isLoading = true,
                error = null
            )

//            delay(1000)
            when (val result = repository.getWeather(city.value)) {
                is Resource.Success -> {
                    _state.value = _state.value.copy(
                        weather = result.data,
                        isLoading = false,
                        error = null
                    )

                    lastUpdate.value = LocalDateTime.now()

                    with(preferences.edit()) {
                        val json = Json.encodeToString(state.value)
                        putString("w", json)
                        putString("d", LocalDateTime.now().toString())
                        apply()
                    }
                }

                is Resource.Error -> {
                    if (result.message?.startsWith("Failed to connect") ?: false) {
                        val weatherJson = preferences.getString("w", null)
                        if (weatherJson != null) {
                            _state.value = Json.decodeFromString<WeatherState>(weatherJson)
                        } else {
                            _state.value = _state.value.copy(
                                weather = null,
                                isLoading = false,
                                error = result.message
                            )
                        }
                    } else {
                        _state.value = _state.value.copy(
                            weather = null,
                            isLoading = false,
                            error = result.message
                        )
                    }
                }
            }
        }
    }
}

@Serializable
data class WeatherState(
    val weather: Weather? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)

fun <VM : ViewModel> viewModelFactory(initializer: () -> VM): ViewModelProvider.Factory {
    return object : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return initializer() as T
        }
    }
}