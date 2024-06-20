package kr.co.domain.repository

import kotlinx.coroutines.flow.Flow
import kr.co.domain.entity.ScheduleEntity

interface ScheduleRepository {
    fun getSchedules(crop: String, year: Int, month: Int):
            Flow<List<ScheduleEntity>>

    fun getSchedules(crop: String, startDate: String):
            Flow<List<ScheduleEntity>>

    // TODO 일정 추가/편집/삭제
}