package kr.co.remote

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kr.co.data.model.data.HolidayData
import kr.co.data.source.remote.HolidayRemoteDataSource
import kr.co.remote.mapper.HolidayRemoteMapper
import kr.co.remote.retrofit.api.HolidayApi
import javax.inject.Inject

internal class HolidayRemoteDataSourceImpl @Inject constructor(
    private val api: HolidayApi,
) : HolidayRemoteDataSource {
    override suspend fun get(year: Int, month: Int): Flow<List<HolidayData>> {
        return combine(
            api.getHoliday(
                year = year,
                month = month
            ).let(HolidayRemoteMapper::convert),
            api.getSolarTerm(
                year = year,
                month = month
            ).let(HolidayRemoteMapper::convert),
            api.getAnniversary(
                year = year,
                month = month
            ).let(HolidayRemoteMapper::convert),
            api.getEtc(
                year = year,
                month = month
            ).let(HolidayRemoteMapper::convert),
        ) {
            a, b, c, d ->
            listOf(a, b, c, d)
        }
    }
}
