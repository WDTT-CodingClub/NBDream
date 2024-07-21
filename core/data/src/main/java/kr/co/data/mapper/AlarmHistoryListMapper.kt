package kr.co.data.mapper

import kr.co.common.mapper.Mapper
import kr.co.data.model.data.AlarmHistoryListData
import kr.co.domain.entity.AlarmHistoryEntity

internal object AlarmHistoryListMapper : Mapper<AlarmHistoryListData, List<AlarmHistoryEntity>> {
    override fun convert(param: AlarmHistoryListData): List<AlarmHistoryEntity> =
        param.items.map {
            AlarmHistoryEntity(
                id = it.id,
                alarmType = it.alarmType,
                title = it.title,
                content = it.content,
                checked = it.checked
            )
        }
}
