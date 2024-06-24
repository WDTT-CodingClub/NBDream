package kr.co.main.calendar.providers

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import kr.co.domain.entity.type.HolidayType
import kr.co.main.model.calendar.CropModel
import kr.co.main.model.calendar.DiaryModel
import kr.co.main.model.calendar.HolidayModel
import kr.co.main.model.calendar.WeatherForecastModel
import kr.co.main.model.calendar.type.CropModelColorType
import kr.co.main.model.calendar.type.CropModelType
import kr.co.main.model.calendar.type.WorkDescriptionModelType
import java.time.LocalDate

internal class FakeDiaryModelProvider : PreviewParameterProvider<DiaryModel> {
    override val values = sequenceOf(
        DiaryModel(
            id = 1,
            crop = CropModel(
                type = CropModelType.POTATO,
                color = CropModelColorType.POTATO
            ),
            registerDate = LocalDate.of(2024, 5, 6),
            holidays = listOf(
                HolidayModel(
                    date = LocalDate.of(2024, 5, 6),
                    name = "입하(立夏)",
                    type = HolidayType.SOLAR_TERM,
                    isHoliday = false
                ),
                HolidayModel(
                    date = LocalDate.of(2024, 5, 6),
                    name = "어린이날",
                    type = HolidayType.NATIONAL_HOLIDAY,
                    isHoliday = true
                )
            ),
            weatherForecast = WeatherForecastModel(
                date = LocalDate.of(2024, 5, 6),
                sky = WeatherForecastModel.Sky.SUNNY,
                minTemp = "25°C",
                maxTemp = "28°C",
                precipitation = 1
            ),
            workLaborer = 4,
            workHours = 6,
            workArea = 80,
            workDescriptions = listOf(
                DiaryModel.WorkDescriptionModel(
                    id = 1,
                    WorkDescriptionModelType.WEED,
                    "감자밭 제초 작업"
                ),
                DiaryModel.WorkDescriptionModel(
                    id = 2,
                    WorkDescriptionModelType.HARVEST,
                    "봄 감자 수확"
                )
            ),
            images = emptyList(),
            memo = "오늘은 감자밭 제초 작업을 하고 봄 감자 수확을 했다. 4명이서 80평 밭에 작업을 하니 6시간이 걸렸다. 더 빠르게 작업을 끝내려면 역시 작업 인원을 늘려야할 것 같다. 내일은 감자밭 물 관리를 하고 남은 봄감자 수확을 해야지."
        )
    )

    override val count: Int = values.count()
}