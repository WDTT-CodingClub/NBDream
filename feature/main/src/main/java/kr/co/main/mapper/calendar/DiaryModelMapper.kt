package kr.co.main.mapper.calendar

import kr.co.common.mapper.BaseMapper
import kr.co.domain.entity.DiaryEntity
import kr.co.main.model.calendar.DiaryModel

internal object DiaryModelMapper
    : BaseMapper<DiaryEntity, DiaryModel>() {
    override fun toRight(left: DiaryEntity): DiaryModel =
        with(left) {
            DiaryModel(
                id = id,
                date = date,
                holidays = holidays.map { HolidayModelMapper.toRight(it) },
                weatherInfo = weatherInfo,
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
                date = date,
                holidays = holidays.map { HolidayModelMapper.toLeft(it) },
                weatherInfo = weatherInfo,
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