package kr.co.impl

import kotlinx.coroutines.flow.Flow
import kr.co.domain.entity.CropEntity
import kr.co.domain.entity.FarmWorkEntity
import kr.co.domain.repository.FarmWorkRepository
import kr.co.domain.usecase.GetFarmWork
import javax.inject.Inject

internal class GetFarmWorkImpl @Inject constructor(
    private val farmWorkRepository: FarmWorkRepository
) : GetFarmWork {
    override suspend fun invoke(crop: CropEntity, month: Int): Flow<List<FarmWorkEntity>> {
        TODO("Not yet implemented")
    }
}