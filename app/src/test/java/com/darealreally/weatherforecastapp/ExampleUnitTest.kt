package com.darealreally.weatherforecastapp

import com.darealreally.weatherforecastapp.data.WeatherRepository
import kotlinx.coroutines.runBlocking
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class AppUnitTest {
    @Test
    fun weatherServiceResponse(): Unit = runBlocking {
        val repo = WeatherRepository()
    }
}
