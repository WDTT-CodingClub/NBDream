package kr.co.domain.usecase.calendar

import kr.co.domain.entity.FarmWorkEntity
import kr.co.domain.repository.FarmWorkRepository
import kr.co.domain.usecase.SuspendUseCase
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetFarmWorksUseCase @Inject constructor(
    private val repository: FarmWorkRepository
) : SuspendUseCase<GetFarmWorksUseCase.Params, List<FarmWorkEntity>>() {
    data class Params(
        val crop: String,
        val month: Int
    )

    override suspend fun build(params: Params?): List<FarmWorkEntity> {
        checkNotNull(params)
        return repository.getFarmWorks(params.crop, params.month)
    }
}