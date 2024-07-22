package kr.co.domain.usecase.alarm

import kr.co.domain.repository.AlarmRepository
import kr.co.domain.usecase.SuspendUseCase
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CheckAlarmHistoryUseCase @Inject constructor(
    private val alarmRepository: AlarmRepository
) : SuspendUseCase<CheckAlarmHistoryUseCase.Params, Unit>() {
    data class Params(val id: Long)

    override suspend fun build(params: Params?) {
        checkNotNull(params)
        alarmRepository.checkAlarmHistory(params.id)
    }
}
