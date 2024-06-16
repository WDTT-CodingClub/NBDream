package kr.co.main.calendar.model

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.vector.ImageVector
import kr.co.domain.entity.WeatherForecastEntity
import kr.co.main.R
import kr.co.ui.icon.DreamIcon
import kr.co.ui.icon.dreamicon.Edit
import java.time.LocalDate
import java.time.format.DateTimeFormatter

data class WeatherForecastModel(
    val date: LocalDate,
    val sky:Sky,
    val maxTemp: String,
    val minTemp: String,
    val precipitation: Int
){
    // TODO 하늘 상태 별 아이콘
    enum class Sky(
        @StringRes val labelId:Int,
        val icon:ImageVector
    ){
        SUNNY(
            labelId = R.string.feature_main_calendar_sky_sunny,
            icon = DreamIcon.Edit
        ),
        PARTLY_CLOUDY(
            labelId = R.string.feature_main_calendar_sky_partly_cloudy,
            icon = DreamIcon.Edit
        ),
        MOSTLY_CLOUDY(
            labelId = R.string.feature_main_calendar_sky_mostly_cloudy,
            icon = DreamIcon.Edit
        )
    }
}

// TODO WeatherForecastEntity의 List<Weather> 프로퍼티에서 몇 번째 Weather 객체 정보 사용할지 생각하기
// 일단 가장 첫 번째 객체 사용하도록 함, index 중간 값으로 하는게 나을지? 아니면 평균 값을 구할지?
internal fun WeatherForecastEntity.convert() = WeatherForecastModel(
    date = LocalDate.parse(weather.first().day, DateTimeFormatter.ofPattern("yyyyMMdd")),
    sky = when(weather.first().weather){
        "맑음" -> WeatherForecastModel.Sky.SUNNY
        "구름 많음" -> WeatherForecastModel.Sky.PARTLY_CLOUDY
        "흐림" -> WeatherForecastModel.Sky.MOSTLY_CLOUDY
        else -> throw IllegalArgumentException("unknown sky")
    },
    maxTemp = weather.first().maxTemp.toInt().toString() + "°C",
    minTemp = weather.first().minTemp.toInt().toString() + "°C",
    precipitation = precipitation
)

internal fun WeatherForecastModel.convert() = WeatherForecastEntity(
    precipitation = precipitation,
    probability = 0, //meaningless
    humidity = 0, //meaningless,
    temperature = 0.0f, //meaningless
    windSpeed = 0, //meaningless
    weather = listOf(
        WeatherForecastEntity.Weather(
            weather = when(sky){
                WeatherForecastModel.Sky.SUNNY -> "맑음"
                WeatherForecastModel.Sky.PARTLY_CLOUDY -> "구름 많음"
                WeatherForecastModel.Sky.MOSTLY_CLOUDY -> "흐림"
            },
            minTemp = minTemp.substringBefore("°C").toFloat(),
            maxTemp = maxTemp.substringBefore("°C").toFloat(),
            day = date.format(DateTimeFormatter.ofPattern("yyyyMMdd"))
        )
    )
)
