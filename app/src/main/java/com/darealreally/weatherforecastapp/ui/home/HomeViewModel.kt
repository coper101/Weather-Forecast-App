package com.darealreally.weatherforecastapp.ui.home

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.darealreally.weatherforecastapp.data.Coordinates
import com.darealreally.weatherforecastapp.data.WeatherRepository
import com.darealreally.weatherforecastapp.utils.toCelsius
import com.darealreally.weatherforecastapp.utils.toDayOfWeek
import com.darealreally.weatherforecastapp.utils.toMonthOfYear
import kotlinx.coroutines.launch

enum class Location {
    CCK {
        override val coordinates = Coordinates(1.3521, 103.8198)
    };
    abstract val coordinates: Coordinates
}

data class DailyForecast(
    val day: String,
    val month: String,
    val date: String,
    val lowTemp: String,
    val highTemp: String,
    val desc: String
)

class HomeViewModel(
    private val weatherRepository: WeatherRepository = WeatherRepository()
): ViewModel() {

    private val location = Location.CCK
    private val _fiveDayForecasts = mutableStateListOf<DailyForecast>()

    val fiveDayForecasts: List<DailyForecast>
        get() = _fiveDayForecasts

    suspend fun getWeather() {
        viewModelScope.launch {
            val weatherForecast = weatherRepository
                .getWeatherForecast(location.coordinates)

            // get daily forecasts
            weatherForecast?.let { weatherFor ->
                weatherFor.list
                    .groupBy { it.formattedDate.date }
                    .mapValues { (date, hourlyForecasts) ->
                        val sortedMinTemp = hourlyForecasts.sortedBy { it.main.tempMin }
                        val sortedMaxTemp = hourlyForecasts.sortedBy { it.main.tempMax }
                        val firstHourlyForecastDate = hourlyForecasts.first().formattedDate

                        // extract values to be displayed
                        DailyForecast(
                            firstHourlyForecastDate.day.toDayOfWeek(),
                            firstHourlyForecastDate.month.toMonthOfYear(),
                            date.toString(),
                            sortedMinTemp.first().main.tempMin.toCelsius().toString(),
                            sortedMaxTemp.last().main.tempMax.toCelsius().toString(),
                            hourlyForecasts.first().weather[0].description
                        )
                    }
                    .values
                    .also {
                        _fiveDayForecasts.addAll(it)
                    }
            }
        }
    }

}

