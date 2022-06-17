package com.darealreally.weatherforecastapp.ui.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.*
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.darealreally.weatherforecastapp.ui.theme.WeatherForecastAppTheme

/**
 * Stateful
 */
@Composable
fun HomeScreen(
    viewModel: HomeViewModel
) {
    // Side Effect
    LaunchedEffect(Unit) {
        viewModel.getWeather()
    }

    // UI
    HomeScreen(fiveDayForecasts = viewModel.fiveDayForecasts)
}

/**
 * Stateless
 */
@Composable
fun HomeScreen(
    fiveDayForecasts: List<DailyForecast> = emptyList()
) {
    // UI
    Scaffold(
        topBar = { TopBar() }
    ) {

        // Layer 1:
        if (fiveDayForecasts.isEmpty()) {
            ItemPlaceholder(
                modifier = Modifier.padding(top = 25.dp)
            )
        }

        // Layer 2:
        AnimatedVisibility(
            visible = fiveDayForecasts.isNotEmpty(),
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            Content(fiveDayForecasts)
        }

    }
}

@Composable
fun Content(
    fiveDayForecasts: List<DailyForecast>
) {
    LazyColumn(
        state = rememberLazyListState(),
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 12.dp)
    ) {

        // Item 1
        item {
            Text(
                "5-day Forecast",
                fontSize = 22.sp,
                fontWeight = FontWeight.SemiBold,
                letterSpacing = 1.sp,
                modifier = Modifier.padding(top = 10.dp, bottom = 5.dp)
            )
        }

        items(
            fiveDayForecasts
        ) {
            ForecastDayRow(dailyForecast = it)
        }

    }
}

@Composable
fun ItemPlaceholder(
    modifier: Modifier = Modifier
) {
    // Animation
    val infiniteTransition = rememberInfiniteTransition()
    val alpha by infiniteTransition.animateFloat(
        initialValue = 0F,
        targetValue = 0.15F,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    // UI
    Column(
        modifier = modifier
    ) {
        repeat(6) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .padding(horizontal = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxHeight(0.37F)
                        .fillMaxWidth(0.5F)
                        .clip(RoundedCornerShape(50.dp))
                        .background(Color.Black.copy(alpha))
                )
            }
        }
    }
}

@Composable
fun TopBar() {
    TopAppBar {
        Box(
            modifier = Modifier
                .padding(start = 7.dp)
                .size(27.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colors.secondary),
        )
        Text(
            "OpenWeather",
            fontSize = 22.sp,
            fontWeight = FontWeight.SemiBold,
            letterSpacing = 1.5.sp,
            modifier = Modifier.padding(start = 11.dp)
        )
    }
}

@Composable
fun ForecastDayRow(
    modifier: Modifier = Modifier,
    dailyForecast: DailyForecast
) {
    val (day, month, date, lowTemp, highTemp, description) = dailyForecast

    // UI
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                vertical = 14.dp
            ),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        // Row 1: Day
        Text(
            "$day, $month $date",
            fontSize = 16.sp,
        )

        // Row 2: Lows & Highs + Weather Description
        Row(
            modifier = Modifier,
            horizontalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Text(
                "$highTemp / $lowTemp°C",
                fontSize = 16.sp,
            )
            Text(
                description,
                fontSize = 14.sp,
                color = Color.Black.copy(alpha = 0.5F)
            )
        }

    }
}

/**
 * Preview Section
 */
@Preview
@Composable
fun HomeScreenPreview() {
    WeatherForecastAppTheme {
        HomeScreen()
    }
}