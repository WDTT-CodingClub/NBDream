package kr.co.wdtt.nbdream.domain.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.transform
import kr.co.wdtt.nbdream.data.mapper.EntityWrapper
import kr.co.wdtt.nbdream.domain.entity.DreamCrop
import kr.co.wdtt.nbdream.domain.entity.FarmWorkEntity
import kr.co.wdtt.nbdream.domain.repository.FarmWorkRepository
import javax.inject.Inject

class GetFarmWorkImpl @Inject constructor(
    private val farmWorkRepository: FarmWorkRepository
) : GetFarmWork {
    override suspend fun invoke(crop: DreamCrop, month: Int): Flow<List<FarmWorkEntity>> {
        TODO("Not yet implemented")
    }
}