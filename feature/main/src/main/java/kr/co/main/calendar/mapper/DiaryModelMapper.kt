package kr.co.main.calendar.mapper

import kr.co.common.mapper.BaseMapper
import kr.co.domain.entity.DiaryEntity
import kr.co.main.calendar.model.DiaryModel

internal object DiaryModelMapper
    : BaseMapper<DiaryEntity, DiaryModel>() {
    override fun toRight(left: DiaryEntity): DiaryModel =
        with(left) {
            DiaryModel(
                id = id,
                crop = CropModelMapper.toRight(crop),
                registerDate = registerDate,
                holidays = holidays,
                weatherForecast = WeatherForecastModelMapper.toRight(weatherForecast),
                workLaborer = workLaborer,
                workHours = workHours,
                workArea = workArea,
                workDescriptions = workDescriptions.map {
                    WorkDescriptionModelMapper.toRight(it)
                },
                images = images,
                memo = memo
            )
        }

    override fun toLeft(right: DiaryModel): DiaryEntity =
        with(right) {
            DiaryEntity(
                id = id,
                crop = CropModelMapper.toLeft(crop),
                registerDate = registerDate,
                holidays = holidays,
                weatherForecast = WeatherForecastModelMapper.toLeft(weatherForecast),
                workLaborer = workLaborer,
                workHours = workHours,
                workArea = workArea,
                workDescriptions = workDescriptions.map {
                    WorkDescriptionModelMapper.toLeft(it)
                },
                images = images,
                memo = memo
            )
        }
}