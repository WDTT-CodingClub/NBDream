package kr.co.domain.usecase.calendar

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kr.co.domain.entity.CropEntity
import kr.co.domain.entity.type.CropType
import kr.co.domain.usecase.FlowUseCase
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetUserCropsUseCase @Inject constructor(
//    private val repository: UserRepository
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
                CropEntity(CropType.POTATO),
                CropEntity(CropType.SWEET_POTATO),
                CropEntity(CropType.APPLE)
            )
        )
}