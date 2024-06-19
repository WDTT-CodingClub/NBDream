package kr.co.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapLatest
import kr.co.data.mapper.HolidayMapper
import kr.co.data.source.remote.HolidayRemoteDataSource
import kr.co.domain.entity.HolidayEntity
import kr.co.domain.repository.HolidayRepository
import javax.inject.Inject

internal class HolidayRepositoryImpl @Inject constructor(
    private val remote: HolidayRemoteDataSource
) : HolidayRepository {
    override fun getHolidays(year: Int, month: Int): Flow<List<HolidayEntity>> {
        TODO("Not yet implemented")
    }
}