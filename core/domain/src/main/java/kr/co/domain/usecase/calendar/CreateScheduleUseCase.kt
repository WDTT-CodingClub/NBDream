package kr.co.domain.usecase.calendar

import kr.co.domain.entity.type.ScheduleType
import kr.co.domain.repository.ScheduleRepository
import kr.co.domain.usecase.SuspendUseCase
import java.time.LocalDate
import java.time.LocalDateTime
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CreateScheduleUseCase @Inject constructor(
    private val repository: ScheduleRepository
): SuspendUseCase<CreateScheduleUseCase.Params, Unit>(){
    data class Params(
        val category: ScheduleType,
        val title: String,
        val startDate: LocalDate,
        val endDate: LocalDate,
        val memo: String,
        val alarmOn: Boolean,
        val alarmDateTime: LocalDateTime?
    )

    override suspend fun build(params: Params?) {
        checkNotNull(params)
        return repository.createSchedule(
            category = params.category.koreanName,
            title = params.title,
            startDate = params.startDate.toString(),
            endDate = params.endDate.toString(),
            memo = params.memo,
            alarmOn = params.alarmOn,
            alarmDateTime = params.alarmDateTime?.toString() ?: "" // 서버 요청 형식에 맞게 파싱
        )
    }
}