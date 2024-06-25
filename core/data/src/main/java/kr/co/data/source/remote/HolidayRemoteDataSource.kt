package kr.co.data.source.remote

import kr.co.data.model.data.calendar.HolidayData

interface HolidayRemoteDataSource {
    suspend fun fetchList(year: String, month: String): List<HolidayData>
}