package kr.co.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.transform
import kr.co.domain.entity.FarmWorkEntity
import kr.co.domain.repository.FarmWorkRepository
import kr.co.domain.usecase.GetFarmWork
import javax.inject.Inject

//internal class GetFarmWorkImpl @Inject constructor(
//    private val farmWorkRepository: FarmWorkRepository
//) : GetFarmWork {
//    override suspend operator fun invoke(crop: kr.co.domain.entity.DreamCrop, month: Int): Flow<List<FarmWorkEntity>> =
//        farmWorkRepository.getFarmWorkByCrop(crop.cropCode).transform { entityWrapper ->
//            if (entityWrapper is EntityWrapper.Success) {
//                emit(entityWrapper.data.filter { it.isInMonth(month) })
//            }
//        }
//
//    private fun FarmWorkEntity.isInMonth(month: Int) = month in startMonth..endMonth
//}