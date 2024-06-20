package kr.co.domain.usecase.calendar

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kr.co.domain.entity.HolidayEntity
import kr.co.domain.repository.HolidayRepository
import kr.co.domain.usecase.FlowUseCase
import java.time.LocalDate
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetHolidaysUseCase @Inject constructor(
    private val repository: HolidayRepository
):FlowUseCase<GetHolidaysUseCase.Params, List<HolidayEntity>>(){
    data class Params(
        val year: Int,
        val month: Int
    )

    override fun build(params: Params?): Flow<List<HolidayEntity>> {
        if(params == null) throw IllegalArgumentException("params cannot be null")

        //return repository.getHolidays(params.year, params.month)
        return flowOf(
            listOf(
                HolidayEntity(
                    date = LocalDate.of(2024, 5, 1),
                    name = "근로자의 날",
                    type = HolidayEntity.Type.NATIONAL_HOLIDAY,
                    isHoliday = true
                ),
                HolidayEntity(
                    date = LocalDate.of(2024, 5, 5),
                    name = "어린이 날",
                    type = HolidayEntity.Type.NATIONAL_HOLIDAY,
                    isHoliday = true
                ),
                HolidayEntity(
                    date = LocalDate.of(2024, 5, 6),
                    name = "어린이 날 대체휴일",
                    type = HolidayEntity.Type.CONSTITUTION_DAY,
                    isHoliday = true
                ),
                HolidayEntity(
                    date = LocalDate.of(2024, 5, 6),
                    name = "입하(立夏)",
                    type = HolidayEntity.Type.SOLAR_TERM,
                    isHoliday = false
                ),
                HolidayEntity(
                    date = LocalDate.of(2024, 5, 15),
                    name = "부처님 오신 날",
                    type = HolidayEntity.Type.NATIONAL_HOLIDAY,
                    isHoliday = true
                ),
                HolidayEntity(
                    date = LocalDate.of(2024, 5, 21),
                    name = "소만(小滿)",
                    type = HolidayEntity.Type.SOLAR_TERM,
                    isHoliday = false
                )
            )
        )
    }
}