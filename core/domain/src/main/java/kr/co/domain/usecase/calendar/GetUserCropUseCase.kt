package kr.co.domain.usecase.calendar

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kr.co.domain.entity.CropEntity
import kr.co.domain.repository.UserRepository
import kr.co.domain.usecase.FlowUseCase
import javax.inject.Inject

class GetUserCropUseCase @Inject constructor(
    private val repository: UserRepository
) : FlowUseCase<Unit, List<CropEntity>>() {
    override fun build(params: Unit?): Flow<List<CropEntity>> =
//        repository.fetchLocal().transform { userEntity ->
//            emit(
//                userEntity.crops.map { crop ->
//                    CropEntity.getCropEntity(crop)
//                }
//            )
//        }
        flowOf(
            listOf(
                CropEntity(CropEntity.Name.POTATO),
                CropEntity(CropEntity.Name.SWEET_POTATO),
                CropEntity(CropEntity.Name.APPLE)
            )
        )
}