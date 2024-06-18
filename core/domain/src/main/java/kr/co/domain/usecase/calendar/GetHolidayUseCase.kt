package kr.co.domain.usecase.calendar

import kotlinx.coroutines.flow.Flow
import kr.co.domain.entity.HolidayEntity
import kr.co.domain.repository.HolidayRepository
import kr.co.domain.usecase.FlowUseCase
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetHolidayUseCase @Inject constructor(
    private val repository: HolidayRepository
):FlowUseCase<GetHolidayUseCase.Params, List<HolidayEntity>>(){
    data class Params(
        val year: Int,
        val month: Int
    )

    override fun build(params: Params?): Flow<List<HolidayEntity>> {
        if(params == null) throw IllegalArgumentException("params cannot be null")

        return repository.getHolidays(params.year, params.month)
    }
}