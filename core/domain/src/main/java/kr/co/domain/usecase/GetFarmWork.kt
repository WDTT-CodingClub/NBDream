package kr.co.domain.usecase

import kotlinx.coroutines.flow.Flow
import kr.co.domain.entity.FarmWorkEntity
import kr.co.domain.entity.plzLookThisPakage.DreamCrop

interface GetFarmWork {
    suspend operator fun invoke(crop: DreamCrop, month: Int): Flow<List<FarmWorkEntity>>
}