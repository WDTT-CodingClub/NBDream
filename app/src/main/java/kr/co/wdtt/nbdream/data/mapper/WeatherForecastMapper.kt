package kr.co.wdtt.nbdream.data.mapper

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kr.co.wdtt.nbdream.data.remote.dto.WeatherForecastResponse
import kr.co.wdtt.nbdream.domain.entity.WeatherForecastEntity
import javax.inject.Inject

internal class WeatherForecastMapper @Inject constructor(): BaseMapper<WeatherForecastResponse, List<WeatherForecastEntity>>() {
    override fun getSuccess(
        data: WeatherForecastResponse?,
        extra: Any?,
    ): Flow<EntityWrapper.Success<List<WeatherForecastEntity>>> = flow {
        data?.let {
            emit(EntityWrapper.Success(
                data = it.body.convert()
            ))
        } ?: emit(EntityWrapper.Success(emptyList()))
    }


    override fun getFailure(error: Throwable): Flow<EntityWrapper.Fail<List<WeatherForecastEntity>>>
        = flow { emit(EntityWrapper.Fail(error)) }

    private fun WeatherForecastResponse.Body.convert() =
        items.groupBy { it.fcstDate }.map { (forecastDate, items) ->
            WeatherForecastEntity(
                forecastDate = forecastDate,
                weather = items.find { it.category == "SKY" }?.fcstValue ?: "",
                humidity = items.find { it.category == "REH" }?.fcstValue ?: "",
                temperature = items.find { it.category == "TMP" }?.fcstValue ?: "",
                minTemperature = items.find { it.category == "TMN" }?.fcstValue ?: "",
                maxTemperature = items.find { it.category == "TMX" }?.fcstValue ?: "",
                precipitation = items.find { it.category == "POP" }?.fcstValue ?: "",
                windSpeed = items.find { it.category == "WSD" }?.fcstValue ?: ""
            )
        }
}