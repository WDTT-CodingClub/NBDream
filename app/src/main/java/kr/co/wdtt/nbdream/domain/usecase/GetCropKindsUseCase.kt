package kr.co.wdtt.nbdream.domain.usecase

import kotlinx.coroutines.flow.transform
import kr.co.wdtt.nbdream.data.mapper.CalFarmWorkMapper
import kr.co.wdtt.nbdream.data.source.remote.ApiResponse
import kr.co.wdtt.nbdream.domain.entity.DreamCrop
import kr.co.wdtt.nbdream.domain.repository.CalFarmWorkRepo
import javax.inject.Inject

class GetCropKindsUseCase @Inject constructor(
    private val dreamCrop: DreamCrop,
    private val calFarmWorkRepo: CalFarmWorkRepo,
    private val calFarmWorkMapper: CalFarmWorkMapper
) {
    suspend operator fun invoke() = calFarmWorkRepo.getFarmWorkByCrop(cropCode = dreamCrop.cropCode)
        .transform { apiResponse ->
            if (apiResponse is ApiResponse.Success) {
                emit(
                    apiResponse.data.body.items.map {
                        calFarmWorkMapper.convert(it)
                    }
                )
            }
        }
}