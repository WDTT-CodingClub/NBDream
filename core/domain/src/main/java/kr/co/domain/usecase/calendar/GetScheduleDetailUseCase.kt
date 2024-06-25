package kr.co.domain.usecase.calendar

import kr.co.domain.entity.ScheduleEntity
import kr.co.domain.repository.ScheduleRepository
import kr.co.domain.usecase.SuspendUseCase
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetScheduleDetailUseCase @Inject constructor(
    private val repository: ScheduleRepository
) : SuspendUseCase<GetScheduleDetailUseCase.Params, ScheduleEntity>() {
    data class Params(val id: Int)

    override suspend fun build(params: Params?): ScheduleEntity {
        checkNotNull(params)
        return repository.getScheduleDetail(params.id)
    }
}