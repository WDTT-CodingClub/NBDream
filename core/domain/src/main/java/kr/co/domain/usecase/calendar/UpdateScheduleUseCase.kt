package kr.co.domain.usecase.calendar

import kr.co.domain.entity.type.ScheduleType
import kr.co.domain.repository.ScheduleRepository
import kr.co.domain.usecase.SuspendUseCase
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UpdateScheduleUseCase @Inject constructor(
    private val repository: ScheduleRepository
) : SuspendUseCase<UpdateScheduleUseCase.Params, Unit>() {
    data class Params(
        val id: Long,
        val category: ScheduleType,
        val title: String,
        val startDate: LocalDate,
        val endDate: LocalDate,
        val memo: String,
        val alarmOn: Boolean,
        val alarmDateTime: LocalDateTime
    )

    override suspend fun build(params: Params?) {
        checkNotNull(params)
        return repository.updateSchedule(
            id = params.id,
            category = params.category.koreanName,
            title = params.title,
            startDate = params.startDate.toString(),
            endDate = params.endDate.toString(),
            memo = params.memo,
            alarmOn = params.alarmOn,
            alarmDateTime =
            if (params.alarmOn) params.alarmDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
            else ""
        )
    }
}