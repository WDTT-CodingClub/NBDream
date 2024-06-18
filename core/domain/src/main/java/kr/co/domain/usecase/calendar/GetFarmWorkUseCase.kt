package kr.co.domain.usecase.calendar

import kotlinx.coroutines.flow.Flow
import kr.co.domain.entity.FarmWorkEntity
import kr.co.domain.repository.FarmWorkRepository
import kr.co.domain.usecase.FlowUseCase
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetFarmWorkUseCase @Inject constructor(
    private val repository: FarmWorkRepository
): FlowUseCase<GetFarmWorkUseCase.Params, List<FarmWorkEntity>>() {
    data class Params(
        val crop:String,
        val year: Int,
        val month: Int
    )

    override fun build(params: Params?): Flow<List<FarmWorkEntity>> {
        if(params == null) throw IllegalArgumentException("params can't be null")

        return repository.getFarmWorks(params.crop, params.year, params.month)
    }
}