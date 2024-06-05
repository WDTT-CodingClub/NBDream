package kr.co.wdtt.nbdream.ui.main.calendar.providers

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import kr.co.wdtt.nbdream.domain.entity.DiaryEntity
import kr.co.wdtt.nbdream.domain.entity.DreamCrop
import kr.co.wdtt.nbdream.domain.entity.HolidayEntity
import kr.co.wdtt.nbdream.domain.entity.HolidayType
import kr.co.wdtt.nbdream.domain.entity.ScheduleCategory
import kr.co.wdtt.nbdream.domain.entity.ScheduleEntity
import kr.co.wdtt.nbdream.domain.entity.WeatherForecastEntity
import kr.co.wdtt.nbdream.domain.entity.WorkDescription
import kr.co.wdtt.nbdream.domain.entity.WorkType
import kr.co.wdtt.nbdream.ui.main.calendar.maincalendar.MainCalendarData
import java.time.LocalDate
import java.time.LocalDateTime

@RequiresApi(Build.VERSION_CODES.O)
internal class FakeMainCalendarDataProvider : PreviewParameterProvider<MainCalendarData> {
    override val values: Sequence<MainCalendarData>
        get() = sequenceOf(
            MainCalendarData(
                year = 2024,
                month = 5,
                crop = DreamCrop.POTATO,
                holidays = listOf(
                    HolidayEntity(
                        date = LocalDate.of(2024, 5, 1),
                        name = "근로자의 날",
                        type = HolidayType.NATIONAL_HOLIDAY,
                        isHoliday = true
                    ),
                    HolidayEntity(
                        date = LocalDate.of(2024, 5, 5),
                        name = "어린이 날",
                        type = HolidayType.NATIONAL_HOLIDAY,
                        isHoliday = true
                    ),
                    HolidayEntity(
                        date = LocalDate.of(2024, 5, 6),
                        name = "어린이 날 대체휴일",
                        type = HolidayType.CONSTITUTION_DAY,
                        isHoliday = true
                    ),
                    HolidayEntity(
                        date = LocalDate.of(2024, 5, 6),
                        name = "입하(立夏)",
                        type = HolidayType.SOLAR_TERM,
                        isHoliday = false
                    ),
                    HolidayEntity(
                        date = LocalDate.of(2024, 5, 15),
                        name = "부처님 오신 날",
                        type = HolidayType.NATIONAL_HOLIDAY,
                        isHoliday = true
                    ),
                    HolidayEntity(
                        date = LocalDate.of(2024, 5, 21),
                        name = "소만(小滿)",
                        type = HolidayType.SOLAR_TERM,
                        isHoliday = false
                    )
                ),
                schedules = listOf(
                    ScheduleEntity(
                        category = ScheduleCategory.All,
                        title = "김반장 쉬는 날",
                        startDate = LocalDate.of(2024, 5, 20),
                        endDate = LocalDate.of(2024, 5, 21)
                    ),
                    ScheduleEntity(
                        category = ScheduleCategory.All,
                        title = "농기계 반납일",
                        startDate = LocalDate.of(2024, 5, 17),
                        isAlarmOn = true,
                        alarmDateTime = LocalDateTime.of(2024, 5, 17, 9, 0, 0)
                    ),
                    ScheduleEntity(
                        category = ScheduleCategory.Crop(DreamCrop.POTATO),
                        title = "감자 물관리 작업",
                        startDate = LocalDate.of(2024, 5, 16),
                        endDate = LocalDate.of(2024, 5, 21)
                    ),
                    ScheduleEntity(
                        category = ScheduleCategory.Crop(DreamCrop.POTATO),
                        title = "감자 캐는 날",
                        startDate = LocalDate.of(2024, 5, 21),
                        memo = "봄 감자, 알 감자 수확",
                        isAlarmOn = true,
                        alarmDateTime = LocalDateTime.of(2024, 5, 21, 9, 0, 0)
                    )
                ),
                diaries = mutableListOf<DiaryEntity>().apply {
                    val diary = DiaryEntity(
                        id = "1",
                        dreamCrop = DreamCrop.POTATO,
                        registerDate = LocalDate.of(2024, 5, 6),
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
                    listOf(1,2,3,6,7,8,10,12,17,18,20,21).forEach { dayOfMonth ->
                        add(diary.copy(registerDate = LocalDate.of(2024, 5, dayOfMonth)))
                    }
                }
            )
        )
    override val count = values.count()
}