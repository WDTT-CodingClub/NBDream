package kr.co.wdtt.nbdream.data.repository

import kotlinx.coroutines.flow.Flow
import kr.co.wdtt.nbdream.BuildConfig
import kr.co.wdtt.nbdream.data.mapper.EntityWrapper
import kr.co.wdtt.nbdream.data.mapper.WeatherForecastMapper
import kr.co.wdtt.nbdream.data.remote.api.NetworkApi
import kr.co.wdtt.nbdream.data.remote.dto.WeatherForecastResponse
import kr.co.wdtt.nbdream.data.remote.retrofit.INetworkFactoryManager
import kr.co.wdtt.nbdream.domain.entity.WeatherForecastEntity
import kr.co.wdtt.nbdream.domain.repository.WeatherForecastRepository
import javax.inject.Inject

internal class WeatherForecastRepositoryImpl @Inject constructor(
    private val network: INetworkFactoryManager,
    private val mapper: WeatherForecastMapper,
) : WeatherForecastRepository {

    companion object {
        const val BASE_URL = "https://apis.data.go.kr/1360000/"
        const val SHORT = "VilageFcstInfoService_2.0/getVilageFcst"
        const val MID = "MidFcstInfoService/getMidFcst"
        const val HEAD_AUTH = "serviceKey"
        const val HEAD_KEY = BuildConfig.WEATHER_SHORT_KEY
    }

    private val queryMap = mutableMapOf(
        "dataType" to "JSON",
    )

    private val networkApi = NetworkApi.create(
        network.create(BASE_URL),
        HEAD_AUTH,
        HEAD_KEY
    )

    override suspend fun getDayWeatherForecast(
        baseDate: String,
        baseTime: String,
        nx: String,
        ny: String,
    ): Flow<EntityWrapper<List<WeatherForecastEntity>>> =
        mapper.mapFromResult(
            networkApi.sendRequest<WeatherForecastResponse>(
                url = SHORT,
                queryMap = queryMap.apply {
                    put("base_date", baseDate)
                    put("base_time", baseTime)
                    put("nx", nx)
                    put("ny", ny)
                }
            )
        )


    override suspend fun getWeekWeatherForecast(
        baseDate: String,
        nx: String,
        ny: String,
    ): Flow<EntityWrapper<List<WeatherForecastEntity>>> =
        mapper.mapFromResult(
            networkApi.sendRequest<WeatherForecastResponse>(
                url = MID,
                queryMap = queryMap.apply {
                    put("base_date", baseDate)
                    put("nx", nx)
                    put("ny", ny)
                }
            )
        )
}