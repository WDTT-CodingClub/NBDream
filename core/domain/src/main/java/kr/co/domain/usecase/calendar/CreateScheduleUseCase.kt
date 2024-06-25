package kr.co.domain.usecase.calendar

import kr.co.domain.repository.ScheduleRepository
import kr.co.domain.usecase.SuspendUseCase
import java.time.LocalDateTime
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CreateScheduleUseCase @Inject constructor(
    private val repository: ScheduleRepository
): SuspendUseCase<CreateScheduleUseCase.Params, Unit>(){
    data class Params(
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
        return repository.createSchedule(
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