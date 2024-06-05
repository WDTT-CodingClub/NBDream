package kr.co.impl

import kotlinx.coroutines.flow.Flow
import kr.co.domain.entity.plzLookThisPakage.DreamCrop
import kr.co.domain.usecase.GetFarmWork
import kr.co.domain.entity.FarmWorkEntity
import kr.co.domain.repository.FarmWorkRepository
import javax.inject.Inject

class GetFarmWorkImpl @Inject constructor(
    private val farmWorkRepository: FarmWorkRepository
) : GetFarmWork {
    override suspend fun invoke(crop: DreamCrop, month: Int): Flow<List<FarmWorkEntity>> {
        TODO("Not yet implemented")
    }
}