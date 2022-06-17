package com.darealreally.weatherforecastapp

import androidx.compose.runtime.Composable
import com.darealreally.weatherforecastapp.data.WeatherRepository
import com.darealreally.weatherforecastapp.ui.home.HomeScreen
import com.darealreally.weatherforecastapp.ui.home.HomeViewModel

@Composable
fun App() {
    HomeScreen(
        viewModel = HomeViewModel()
    )
}