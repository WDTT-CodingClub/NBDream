package kr.co.data.source.remote

import kr.co.data.model.data.calendar.FarmWorkData

interface FarmWorkRemoteDataSource {
    suspend fun fetchList(crop: String, month: Int): List<FarmWorkData>
}