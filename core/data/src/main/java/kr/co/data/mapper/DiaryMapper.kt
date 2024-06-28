package kr.co.data.mapper

import kr.co.common.mapper.BaseMapper
import kr.co.common.mapper.Mapper
import kr.co.data.model.data.calendar.DiaryData
import kr.co.domain.entity.DiaryEntity
import kr.co.domain.entity.type.WorkDescriptionType
import java.time.LocalDate
import java.time.format.DateTimeFormatter

internal object DiaryMapper
    : Mapper<DiaryData, DiaryEntity> {
    override fun convert(param: DiaryData): DiaryEntity =
        with(param) {
            DiaryEntity(
                id = id,
                date = LocalDate.parse(date),
                holidays = holidayList.map { HolidayMapper.toRight(it) },
                weatherInfo = weatherForecast,
                workLaborer = workLaborer,
                workHours = workHours,
                workArea = workArea,
                workDescriptions = workDescriptions.map { WorkDescriptionMapper.toRight(it) },
                memo = memo
            )
        }

    object WorkDescriptionMapper
        : BaseMapper<DiaryData.WorkDescriptionData, DiaryEntity.WorkDescriptionEntity>() {
        override fun toRight(left: DiaryData.WorkDescriptionData): DiaryEntity.WorkDescriptionEntity =
            with(left) {
                DiaryEntity.WorkDescriptionEntity(
                    type = WorkDescriptionType.ofValue(type),
                    description = description
                )
            }

        override fun toLeft(right: DiaryEntity.WorkDescriptionEntity): DiaryData.WorkDescriptionData =
            with(right){
                DiaryData.WorkDescriptionData(
                    type = type.koreanName,
                    description = description
                )
            }
    }
}