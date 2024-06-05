package kr.co.wdtt.nbdream.ui.main.calendar.providers

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import kr.co.wdtt.nbdream.domain.entity.DiaryEntity
import kr.co.wdtt.nbdream.domain.entity.DreamCrop
import kr.co.wdtt.nbdream.domain.entity.HolidayEntity
import kr.co.wdtt.nbdream.domain.entity.HolidayType
import kr.co.wdtt.nbdream.domain.entity.WeatherForecastEntity
import kr.co.wdtt.nbdream.domain.entity.WorkDescription
import kr.co.wdtt.nbdream.domain.entity.WorkType
import java.time.LocalDate

internal class FakeDiaryEntityProvider: PreviewParameterProvider<DiaryEntity> {
    @RequiresApi(Build.VERSION_CODES.O)
    override val values = sequenceOf(
        DiaryEntity(
            id = "1",
            dreamCrop = DreamCrop.POTATO,
            registerDate = LocalDate.of(2024,5,6),
            holidays = listOf(
                HolidayEntity(
                    date = LocalDate.of(2024, 5, 6),
                    name = "어린이날",
                    type = HolidayType.NATIONAL_HOLIDAY,
                    isHoliday = true
                ),
                HolidayEntity(
                    date = LocalDate.of(2024, 5, 6),
                    name = "입하(立夏)",
                    type = HolidayType.SOLAR_TERM,
                    isHoliday = false
                )
            ),
            weatherForecast = WeatherForecastEntity(
                forecastDate = "20240506",
                weather = "맑음",
                humidity = "30%",
                temperature = "",
                minTemperature = "21°C",
                maxTemperature = "27°C",
                precipitation = "0mm 미만",
                windSpeed = "3m/s"
            ),
            workLaborer = 4,
            workHours = 6,
            workArea = 80,
            workDescriptions = listOf(
                WorkDescription(WorkType.WEED, "감자밭 제초 작업"),
                WorkDescription(WorkType.HARVEST, "봄 감자 수확")
            ),
            images = emptyList(),
            memo = "오늘은 감자밭 제초 작업을 하고 봄 감자 수확을 했다. 4명이서 80평 밭에 작업을 하니 6시간이 걸렸다. 더 빠르게 작업을 끝내려면 역시 작업 인원을 늘려야할 것 같다. 내일은 감자밭 물 관리를 하고 남은 봄감자 수확을 해야지."
        )
    )
    @RequiresApi(Build.VERSION_CODES.O)
    override val count: Int = values.count()
}