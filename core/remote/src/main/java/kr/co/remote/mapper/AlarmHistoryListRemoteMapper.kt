package kr.co.remote.mapper

import kr.co.common.mapper.Mapper
import kr.co.data.model.data.AlarmHistoryListData
import kr.co.remote.model.response.alarm.GetAlarmHistoryListResponse

internal object AlarmHistoryListRemoteMapper :
    Mapper<GetAlarmHistoryListResponse, AlarmHistoryListData> {
    override fun convert(param: GetAlarmHistoryListResponse): AlarmHistoryListData =
        with(param.data) {
            AlarmHistoryListData(
                items = alarmHistoryList.map {
                    AlarmHistoryListData.Item(
                        id = it.id,
                        alarmType = it.alarmType,
                        title = it.title,
                        content = it.content,
                        checked = it.checked,
                        createdDate = it.createdDate,
                        targetId = it.targetId
                    )
                }
            )
        }
}