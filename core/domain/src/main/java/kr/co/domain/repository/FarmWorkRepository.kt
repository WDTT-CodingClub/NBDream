package kr.co.domain.repository

import kr.co.domain.entity.FarmWorkEntity

interface FarmWorkRepository {
    suspend fun getFarmWorks(
        crop: String,
        month: Int
    ): List<FarmWorkEntity>
}