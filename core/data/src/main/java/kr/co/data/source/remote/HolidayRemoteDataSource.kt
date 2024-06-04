package kr.co.data.source.remote

import kotlinx.coroutines.flow.Flow
import kr.co.data.model.data.HolidayResult

interface HolidayRemoteDataSource {
    suspend fun get(year: Int, month: Int): Flow<List<HolidayResult>>
}