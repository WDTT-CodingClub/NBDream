package kr.co.domain.usecase.alarm

import kr.co.domain.entity.AlarmStatusEntity
import kr.co.domain.repository.AlarmRepository
import kr.co.domain.usecase.SuspendUseCase
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AlarmFetchUseCase @Inject constructor(
    private val alarmRepository: AlarmRepository
) : SuspendUseCase<Unit, AlarmStatusEntity>() {
    override suspend fun build(params: Unit?): AlarmStatusEntity {
        return alarmRepository.getAlarmStatus()
    }
}