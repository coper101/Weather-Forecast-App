package com.darealreally.weatherforecastapp.data

import android.util.Log
import retrofit2.Response

class WeatherRepository(
    private var weatherService: WeatherService = createWeatherService()
) {
    private val appId = "1e0ea6dfe95e1a3be3ec23cd504b067e"

    suspend fun getWeatherForecast(
        coordinates: Coordinates
    ): WeatherForecast? {
        val (lat, lon) = coordinates
        return weatherService
            .getWeatherForecast(
                lat.toString(), lon.toString(), appId
            )
            .also { logWeatherForecast(it) }
            .body()
    }
}

fun logWeatherForecast(response: Response<WeatherForecast>) {
    val weatherForecast = response.body()
    if (!response.isSuccessful || weatherForecast == null) {
        Log.e(
            "WeatherRepository",
            "Failed - error response: ${response.code()}, ${response.message()}"
        )
    } else {
        Log.e(
            "WeatherRepository",
            "Success - response: $weatherForecast"
        )
    }
}