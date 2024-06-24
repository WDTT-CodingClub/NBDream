package kr.co.domain.usecase.calendar

import kotlinx.coroutines.flow.Flow
import kr.co.domain.entity.HolidayEntity
import kr.co.domain.repository.HolidayRepository
import kr.co.domain.usecase.SuspendFlowUseCase
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetHolidaysUseCase @Inject constructor(
    private val repository: HolidayRepository
) : SuspendFlowUseCase<GetHolidaysUseCase.Params, List<HolidayEntity>>() {
    data class Params(
        val year: Int,
        val month: Int
    )

    override suspend fun build(params: Params?): Flow<List<HolidayEntity>> {
        checkNotNull(params)
        return repository.getHolidays(params.year, params.month)
    }
}