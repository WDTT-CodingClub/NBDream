package kr.co.data.source.remote

import kotlinx.coroutines.flow.Flow
import kr.co.data.model.data.HolidayData

interface HolidayRemoteDataSource {
    suspend fun fetchList(year: String, month: String): Flow<List<HolidayData>>
}