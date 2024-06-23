package kr.co.remote.mapper.calendar

import kr.co.common.mapper.Mapper
import kr.co.data.model.data.calendar.FarmWorkData
import kr.co.remote.model.response.calendar.FarmWorkListResponse

internal object FarmWorkRemoteMapper:
    Mapper<FarmWorkListResponse.FarmWorkResponse, FarmWorkData> {
    override fun convert(param: FarmWorkListResponse.FarmWorkResponse): FarmWorkData =
        with(param){
            FarmWorkData(
                id = id,
                //crop = crop,
                startMonth = startMonth,
                startEra = startEra,
                endMonth = endMonth,
                endEra = endEra,
                farmWorkCategory = farmWorkCategory,
                farmWork = farmWork,
                videoUrl = videoUrl
            )
        }
}