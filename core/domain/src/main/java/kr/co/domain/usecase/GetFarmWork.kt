package kr.co.domain.usecase

import kotlinx.coroutines.flow.Flow
import kr.co.domain.entity.CropEntity
import kr.co.domain.entity.FarmWorkEntity

interface GetFarmWork {
    suspend operator fun invoke(crop: CropEntity, month: Int): Flow<List<FarmWorkEntity>>
}