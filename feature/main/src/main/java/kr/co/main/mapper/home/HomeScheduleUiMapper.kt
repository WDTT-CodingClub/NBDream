package kr.co.main.mapper.home

import kr.co.common.mapper.Mapper
import kr.co.domain.entity.ScheduleEntity
import kr.co.main.home.HomeViewModel

internal object HomeScheduleUiMapper
    :Mapper<ScheduleEntity, HomeViewModel.State.Schedule> {
    override fun convert(param: ScheduleEntity): HomeViewModel.State.Schedule {
        return with(param) {
            HomeViewModel.State.Schedule(
                id = id,
                title = title,
                startDate = startDate,
                endDate = endDate
            )
        }
    }
}