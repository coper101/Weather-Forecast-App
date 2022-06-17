package com.darealreally.weatherforecastapp.data

import com.darealreally.weatherforecastapp.utils.toDate
import com.google.gson.annotations.SerializedName
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.*

interface WeatherService {
    @GET("data/2.5/forecast")
    suspend fun getWeatherForecast(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("appId") appId: String
    ): Response<WeatherForecast>
}

fun createWeatherService(): WeatherService {
    val retrofit = Retrofit.Builder()
        .baseUrl("https://api.openweathermap.org/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    return retrofit.create(WeatherService::class.java)
}

// PARENT
data class WeatherForecast(
    val cod: String,
    val message: Int,
    val cnt: Int,
    val list: List<Forecast>,
    val city: City
)

// City
data class City(
    val id: Int,
    val name: String,
    val coordinates: Coordinates,
    val country: String,
    val population: Int,
    @SerializedName("timezone") val timeZone: Int,
    val sunrise: Int,
    val sunset: Int
)

data class Coordinates(
    val lat: Double,
    val lon: Double
)

// Forecast List
data class Forecast(
    val dt: Int,
    val main: Main,
    val weather: List<Weather>,
    val clouds: Cloud,
    val wind: Wind,
    val visibility: Int,
    val pop: Double,
    val rain: Rain,
    val sys: Sys,
    @SerializedName("dt_txt") val dateTimeText: String
) {
    val formattedDate: Date
        get() = dateTimeText.toDate() ?: Date()
}

data class Main(
    val temp: Double,
    @SerializedName("feels_like") val feelsLike: Double,
    @SerializedName("temp_min") val tempMin: Double,
    @SerializedName("temp_max") val tempMax: Double,
    val pressure: Int,
    @SerializedName("sea_level") val seaLevel: Int,
    @SerializedName("grnd_level") val groundLevel: Int,
    val humidity: Int,
    @SerializedName("temp_kf") val tempKf: Double
)

data class Weather(
    val id: Int,
    val main: String,
    val description: String,
    val icon: String
)

data class Cloud(
    val all: Int
)

data class Wind(
    val speed: Double,
    val deg: Int,
    val gust: Double
)

data class Rain(
    @SerializedName("3h") val threeHr: Double
)

data class Sys(
    val pod: String
)
