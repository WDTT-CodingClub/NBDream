package kr.co.domain.usecase.calendar

import kr.co.domain.entity.DiaryEntity
import kr.co.domain.entity.HolidayEntity
import kr.co.domain.repository.DiaryRepository
import kr.co.domain.usecase.SuspendUseCase
import java.time.LocalDate
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CreateDiaryUseCase @Inject constructor(
    private val repository: DiaryRepository
): SuspendUseCase<CreateDiaryUseCase.Params, Unit>() {
    data class Params(
        val crop:String,
        val date: LocalDate,
        val memo: String,
        val workDescriptions: List<DiaryEntity.WorkDescriptionEntity>,
        val imageUrls: List<String>,
        val workLaborer: Int,
        val workHours: Int,
        val workArea: Int,
        val weatherForecast: String,
        val holidayList: List<HolidayEntity>

    )

    override suspend fun build(params: Params?) {
        checkNotNull(params)
        return repository.createDiary(
            crop = params.crop,
            date = params.date,
            holidayList = params.holidayList,
            imageUrls = params.imageUrls,
            weatherForecast = params.weatherForecast,
            workLaborer = params.workLaborer,
            workHours = params.workHours,
            workArea = params.workArea,
            workDescriptions = params.workDescriptions,
            memo = params.memo
        )
    }
}