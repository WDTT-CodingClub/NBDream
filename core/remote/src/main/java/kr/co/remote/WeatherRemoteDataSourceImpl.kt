package kr.co.remote

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kr.co.data.model.data.WeatherForecastData
import kr.co.data.source.remote.WeatherRemoteDataSource
import kr.co.remote.mapper.WeatherRemoteMapper
import kr.co.remote.model.response.GetWeatherForecastResponse
import javax.inject.Inject

internal class WeatherRemoteDataSourceImpl @Inject constructor(
    private val client: HttpClient,
) : WeatherRemoteDataSource {

    companion object {
        private const val WEATHER_URL = "api/auth/weather"
    }

    override suspend fun get(): WeatherForecastData {
        return client.get(WEATHER_URL)
            .body<GetWeatherForecastResponse>()
            .let(WeatherRemoteMapper::convert)
    }
}