package kr.co.wdtt.nbdream.domain.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.transform
import kr.co.wdtt.nbdream.data.mapper.EntityWrapper
import kr.co.wdtt.nbdream.domain.entity.HolidayEntity
import kr.co.wdtt.nbdream.domain.repository.HolidayRepository
import javax.inject.Inject

class GetHolidayUseCase @Inject constructor(
    private val holidayRepository: HolidayRepository
) {
    // Flow<List<EntityWrapper<List<HolidayEntity>>>>
    // Flow<List<HolidayEntity>>
    suspend operator fun invoke(year: Int, month: Int): Flow<List<HolidayEntity>> =
        holidayRepository.getHolidays(year, month).transform { entityWrapperList ->
            emit(
                entityWrapperList.flatMap { entityWrapper ->
                    if (entityWrapper is EntityWrapper.Success) entityWrapper.data
                    else emptyList()
                }
            )
        }
}