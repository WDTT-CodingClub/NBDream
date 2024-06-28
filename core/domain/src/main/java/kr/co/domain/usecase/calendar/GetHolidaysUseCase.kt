package kr.co.domain.usecase.calendar

import kr.co.domain.entity.HolidayEntity
import kr.co.domain.repository.HolidayRepository
import kr.co.domain.usecase.SuspendUseCase
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetHolidaysUseCase @Inject constructor(
    private val repository: HolidayRepository
) : SuspendUseCase<GetHolidaysUseCase.Params, List<HolidayEntity>>() {
    data class Params(
        val year: Int,
        val month: Int
    )

    override suspend fun build(params: Params?): List<HolidayEntity> {
        checkNotNull(params)
        return repository.getHolidays(params.year, params.month).distinct()
    }
}