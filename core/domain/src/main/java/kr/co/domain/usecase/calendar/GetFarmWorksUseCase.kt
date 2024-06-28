package kr.co.domain.usecase.calendar

import kr.co.domain.entity.FarmWorkEntity
import kr.co.domain.entity.type.CropType
import kr.co.domain.repository.FarmWorkRepository
import kr.co.domain.usecase.SuspendUseCase
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetFarmWorksUseCase @Inject constructor(
    private val repository: FarmWorkRepository
) : SuspendUseCase<GetFarmWorksUseCase.Params, List<FarmWorkEntity>>() {
    data class Params(
        val crop: CropType?,
        val month: Int
    )

    override suspend fun build(params: Params?): List<FarmWorkEntity> {
        Timber.d("build) params: ${params}")
        checkNotNull(params)
        checkNotNull(params.crop)

        return repository.getFarmWorks(params.crop.koreanName, params.month).apply {
            Timber.d("build) farmworks: ${map{it.farmWork}}")
        }
    }
}