package kr.co.domain.repository

import kotlinx.coroutines.flow.Flow
import kr.co.domain.entity.FarmWorkEntity

interface FarmWorkRepository {
    fun getFarmWorks(
        crop: String,
        year:Int,
        month:Int
    ): Flow<List<FarmWorkEntity>>
}