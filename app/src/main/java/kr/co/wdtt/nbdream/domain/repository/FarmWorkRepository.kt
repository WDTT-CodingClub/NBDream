package kr.co.wdtt.nbdream.domain.repository

import kotlinx.coroutines.flow.Flow
import kr.co.wdtt.nbdream.data.mapper.EntityWrapper
import kr.co.wdtt.nbdream.domain.entity.FarmWorkEntity

interface FarmWorkRepository {
    suspend fun getFarmWorks(
        crop: String,
        year:Int,
        month:Int
    ): Flow<List<FarmWorkEntity>>
}