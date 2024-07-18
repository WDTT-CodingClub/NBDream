package kr.co.domain.usecase.notification

import kr.co.domain.entity.AlarmStatusEntity
import kr.co.domain.repository.AlarmRepository
import kr.co.domain.usecase.SuspendUseCase
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AlarmUpdateUseCase @Inject constructor(
    private val alarmRepository: AlarmRepository
) : SuspendUseCase<AlarmUpdateUseCase.Params, AlarmStatusEntity>() {
    data class Params(
        val commentAlarm: Boolean,
        val scheduleAlarm: Boolean
    )

    override suspend fun build(params: Params?): AlarmStatusEntity {
        checkNotNull(params)
        return alarmRepository.updateAlarmSettings(params.commentAlarm, params.scheduleAlarm)
    }
}