package kr.co.wdtt.nbdream.domain.usecase

import kotlinx.coroutines.flow.Flow
import kr.co.wdtt.nbdream.domain.entity.DreamCrop
import kr.co.wdtt.nbdream.domain.entity.FarmWorkEntity

interface GetFarmWork {
    suspend operator fun invoke(crop:DreamCrop, month: Int): Flow<List<FarmWorkEntity>>
}