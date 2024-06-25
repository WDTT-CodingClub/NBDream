package kr.co.domain.usecase.calendar

import kr.co.domain.entity.DiaryEntity
import kr.co.domain.entity.HolidayEntity
import kr.co.domain.repository.DiaryRepository
import kr.co.domain.usecase.SuspendUseCase
import java.time.LocalDate
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UpdateDiaryUseCase @Inject constructor(
    private val repository: DiaryRepository
): SuspendUseCase<UpdateDiaryUseCase.Params, Unit>() {
    data class Params(
        val id:Int,
        val date: LocalDate,
        val holidayList: List<HolidayEntity>,
        val weatherForecast: String,
        val workLaborer: Int,
        val workHours: Int,
        val workArea: Int,
        val workDescriptions: List<DiaryEntity.WorkDescriptionEntity>,
        val memo: String
    )

    override suspend fun build(params: Params?) {
        checkNotNull(params)
        return repository.updateDiary(
            id = params.id,
            date = params.date,
            holidayList = params.holidayList,
            weatherForecast = params.weatherForecast,
            workLaborer = params.workLaborer,
            workHours = params.workHours,
            workArea = params.workArea,
            workDescriptions = params.workDescriptions,
            memo = params.memo
        )
    }
}