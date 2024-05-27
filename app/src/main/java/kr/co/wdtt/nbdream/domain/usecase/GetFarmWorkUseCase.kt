package kr.co.wdtt.nbdream.domain.usecase

import kr.co.wdtt.nbdream.domain.repository.FarmWorkRepository
import javax.inject.Inject

class GetFarmWorkUseCase @Inject constructor(
    private val farmWorkRepository: FarmWorkRepository
){
    suspend operator fun invoke(cropCode:String) =
        farmWorkRepository.getFarmWorkByCrop(cropCode)
}