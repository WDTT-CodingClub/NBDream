//package kr.co.impl
//
//import kotlinx.coroutines.flow.Flow
//import kotlinx.coroutines.flow.combine
//import kr.co.domain.entity.HolidayEntity
//import kr.co.domain.repository.HolidayRepository
//import kr.co.domain.usecase.GetHoliday
//import javax.inject.Inject
//
//class GetHolidayImpl @Inject constructor(
//    private val holidayRepository: HolidayRepository
//): GetHoliday {
//    private val TAG = this@GetHolidayImpl::class.java.simpleName
//
//    override suspend operator fun invoke(year: Int, month: Int): Flow<List<HolidayEntity>> =
//        combine(
//            holidayRepository.getHoliday(year, month),
//            holidayRepository.getAnniversary(year, month),
//            holidayRepository.getSolarTerm(year, month),
//            holidayRepository.getEtc(year, month),
//        ) { holidaysArray ->
//            holidaysArray.toList().flatMap{ it.toList() }
//        }
//
//    private fun EntityWrapper<List<HolidayEntity>>.toList(): List<HolidayEntity> =
//        if (this is EntityWrapper.Success) data else  emptyList()
//
//}