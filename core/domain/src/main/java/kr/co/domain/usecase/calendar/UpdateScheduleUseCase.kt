package kr.co.domain.usecase.calendar

import kr.co.domain.repository.ScheduleRepository
import kr.co.domain.usecase.SuspendUseCase
import java.time.LocalDateTime
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UpdateScheduleUseCase @Inject constructor(
    private val repository: ScheduleRepository
) : SuspendUseCase<UpdateScheduleUseCase.Params, Unit>() {
    data class Params(
        val id: Int,
        val category: String,
        val title: String,
        val startDate: String,
        val endDate: String,
        val memo: String,
        val isAlarmOn: Boolean = false,
        val alarmDateTime: String = LocalDateTime.now().toString()
    )

    override suspend fun build(params: Params?) {
        checkNotNull(params)
        return repository.updateSchedule(
            id = params.id,
            category = params.category,
            title = params.title,
            startDate = params.startDate,
            endDate = params.endDate,
            memo = params.memo,
            isAlarmOn = params.isAlarmOn,
            alarmDateTime = params.alarmDateTime
        )
    }
}