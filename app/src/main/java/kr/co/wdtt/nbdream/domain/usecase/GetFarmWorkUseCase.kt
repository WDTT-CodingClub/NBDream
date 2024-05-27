package kr.co.wdtt.nbdream.domain.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.transform
import kr.co.wdtt.nbdream.data.mapper.EntityWrapper
import kr.co.wdtt.nbdream.domain.entity.FarmWorkEntity
import kr.co.wdtt.nbdream.domain.repository.FarmWorkRepository
import javax.inject.Inject

class GetFarmWorkUseCase @Inject constructor(
    private val farmWorkRepository: FarmWorkRepository
){
    suspend operator fun invoke(cropCode:String): Flow<List<FarmWorkEntity>> =
        farmWorkRepository.getFarmWorkByCrop(cropCode).transform { entityWrapper ->
            if(entityWrapper is EntityWrapper.Success){
                emit(entityWrapper.data)
            }
        }
}