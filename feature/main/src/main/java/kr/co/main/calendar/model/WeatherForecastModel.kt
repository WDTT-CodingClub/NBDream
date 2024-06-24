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
    enum class Sky(
        @StringRes val labelId:Int
    ){
        SUNNY(
            labelId = R.string.feature_main_calendar_sky_sunny
        ),
        PARTLY_CLOUDY(
            labelId = R.string.feature_main_calendar_sky_partly_cloudy
        ),
        MOSTLY_CLOUDY(
            labelId = R.string.feature_main_calendar_sky_mostly_cloudy
        )
    }
}