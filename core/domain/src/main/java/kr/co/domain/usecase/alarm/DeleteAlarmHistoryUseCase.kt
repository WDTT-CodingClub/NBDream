package kr.co.domain.usecase.alarm

import kr.co.domain.repository.AlarmRepository
import kr.co.domain.usecase.SuspendUseCase
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DeleteAlarmHistoryUseCase @Inject constructor(
    private val alarmRepository: AlarmRepository
) : SuspendUseCase<DeleteAlarmHistoryUseCase.Params, Unit>() {
    data class Params(val ids: List<Long>)

    override suspend fun build(params: Params?) {
        checkNotNull(params)
        alarmRepository.deleteAlarmHistory(params.ids)
    }
}
