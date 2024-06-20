package kr.co.domain.usecase.calendar

import kotlinx.coroutines.flow.Flow
import kr.co.domain.entity.ScheduleEntity
import kr.co.domain.repository.ScheduleRepository
import kr.co.domain.usecase.FlowUseCase
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetSchedulesUseCase @Inject constructor(private val repository: ScheduleRepository
) : FlowUseCase<GetSchedulesUseCase.Params, List<ScheduleEntity>>() {

    sealed class Params {
        data class Weekly(
            val crop: String,
            val startDate: String
        ) : Params()

        data class Monthly(
            val crop: String,
            val year: Int,
            val month: Int,
        ) : Params()
    }

    override fun build(params: Params?): Flow<List<ScheduleEntity>> {
        if (params == null) throw IllegalArgumentException("params can't be null")
        return when (params) {
            is Params.Weekly -> repository.getSchedules(params.crop, params.startDate)
            is Params.Monthly -> repository.getSchedules(params.crop, params.year, params.month)
        }
    }
}