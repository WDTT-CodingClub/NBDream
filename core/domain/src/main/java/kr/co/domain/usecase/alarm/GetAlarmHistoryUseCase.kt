package kr.co.domain.usecase.alarm

import kr.co.domain.entity.AlarmHistoryEntity
import kr.co.domain.repository.AlarmRepository
import kr.co.domain.usecase.SuspendUseCase
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetAlarmHistoryUseCase @Inject constructor(
    private val alarmRepository: AlarmRepository
) : SuspendUseCase<Unit, List<AlarmHistoryEntity>>() {
    override suspend fun build(params: Unit?): List<AlarmHistoryEntity> {
        return alarmRepository.getAlarmHistory()
    }
}
