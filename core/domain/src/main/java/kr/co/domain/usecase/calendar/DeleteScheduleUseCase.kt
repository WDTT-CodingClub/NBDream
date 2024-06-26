package kr.co.domain.usecase.calendar

import kr.co.domain.repository.ScheduleRepository
import kr.co.domain.usecase.SuspendUseCase
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DeleteScheduleUseCase @Inject constructor(
    private val repository: ScheduleRepository
) : SuspendUseCase<DeleteScheduleUseCase.Params, Unit>() {
    data class Params(
        val id: Long
    )

    override suspend fun build(params: Params?) {
        checkNotNull(params)
        return repository.deleteSchedule(params.id)
    }
}