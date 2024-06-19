package kr.co.domain.usecase.calendar

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kr.co.domain.entity.CropEntity
import kr.co.domain.entity.FarmWorkEntity
import kr.co.domain.repository.FarmWorkRepository
import kr.co.domain.usecase.FlowUseCase
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetFarmWorkUseCase @Inject constructor(
    private val repository: FarmWorkRepository
) : FlowUseCase<GetFarmWorkUseCase.Params, List<FarmWorkEntity>>() {
    data class Params(
        val crop: String,
        val year: Int,
        val month: Int
    )

    override fun build(params: Params?): Flow<List<FarmWorkEntity>> {
        if (params == null) throw IllegalArgumentException("params can't be null")
        //return repository.getFarmWorks(params.crop, params.year, params.month)
        return flowOf(
            listOf(
                FarmWorkEntity(
                    id = "1",
                    crop = CropEntity(CropEntity.Name.POTATO),
                    startEra = FarmWorkEntity.Era.EARLY,
                    endEra = FarmWorkEntity.Era.EARLY,
                    category = FarmWorkEntity.Category.GROWTH,
                    farmWork = "경엽신장기",
                    videoUrl = ""
                ),
                FarmWorkEntity(
                    id = "2",
                    crop = CropEntity(CropEntity.Name.POTATO),
                    startEra = FarmWorkEntity.Era.MID,
                    endEra = FarmWorkEntity.Era.LATE,
                    category = FarmWorkEntity.Category.GROWTH,
                    farmWork = "배수, 병해충방제",
                    videoUrl = ""
                ),
                FarmWorkEntity(
                    id = "3",
                    crop = CropEntity(CropEntity.Name.POTATO),
                    startEra = FarmWorkEntity.Era.LATE,
                    endEra = FarmWorkEntity.Era.LATE,
                    category = FarmWorkEntity.Category.GROWTH,
                    farmWork = "덩이줄기비대기",
                    videoUrl = ""
                ),
                FarmWorkEntity
                    (
                    id = "4",
                    crop = CropEntity(CropEntity.Name.POTATO),
                    startEra = FarmWorkEntity.Era.EARLY,
                    endEra = FarmWorkEntity.Era.LATE,
                    category = FarmWorkEntity.Category.CLIMATE,
                    farmWork = "가뭄",
                    videoUrl = ""
                ),
                FarmWorkEntity
                    (
                    id = "5",
                    crop = CropEntity(CropEntity.Name.POTATO),
                    startEra = FarmWorkEntity.Era.EARLY,
                    endEra = FarmWorkEntity.Era.LATE,
                    category = FarmWorkEntity.Category.CLIMATE,
                    farmWork = "진딧물다발 덩이줄기 비대불량",
                    videoUrl = ""
                ),
                FarmWorkEntity
                    (
                    id = "6",
                    crop = CropEntity(CropEntity.Name.POTATO),
                    startEra = FarmWorkEntity.Era.EARLY,
                    endEra = FarmWorkEntity.Era.LATE,
                    category = FarmWorkEntity.Category.PEST,
                    farmWork = "진딧물(바이러스병)",
                    videoUrl = ""
                )
            )
        )
    }
}