package kr.co.domain.usecase

import kotlinx.coroutines.flow.Flow
import kr.co.domain.entity.FarmWorkEntity

interface GetFarmWork {
    suspend operator fun invoke(crop: kr.co.domain.entity.DreamCrop, month: Int): Flow<List<FarmWorkEntity>>
}