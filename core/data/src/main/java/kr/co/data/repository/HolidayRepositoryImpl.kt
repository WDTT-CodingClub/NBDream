package kr.co.data.repository

import kr.co.data.mapper.HolidayMapper
import kr.co.data.source.remote.HolidayRemoteDataSource
import kr.co.domain.entity.HolidayEntity
import kr.co.domain.repository.HolidayRepository
import javax.inject.Inject

internal class HolidayRepositoryImpl @Inject constructor(
    private val remote: HolidayRemoteDataSource
) : HolidayRepository {
    override suspend fun getHolidays(year: Int, month: Int): List<HolidayEntity> =
        remote.fetchList(
            year = String.format("%02d", year),
            month = String.format("%02d", month)
        ).map { holidayData -> HolidayMapper.toRight(holidayData) }
}