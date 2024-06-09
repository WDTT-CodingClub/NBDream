package kr.co.domain.usecase

import kotlinx.coroutines.flow.Flow
import kr.co.domain.entity.HolidayEntity

interface GetHoliday {
    suspend operator fun invoke(year: Int, month: Int): Flow<List<HolidayEntity>>
}