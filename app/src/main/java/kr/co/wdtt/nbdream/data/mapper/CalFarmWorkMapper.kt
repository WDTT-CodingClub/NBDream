package kr.co.wdtt.nbdream.data.mapper

import kr.co.wdtt.nbdream.data.source.remote.nsrfarmwork.dto.FarmWorkByCropItem
import kr.co.wdtt.nbdream.domain.entity.CalFarmWorkEntity
import kr.co.wdtt.nbdream.domain.entity.CropScheduleEra
import kr.co.wdtt.nbdream.domain.entity.DreamCrop

class CalFarmWorkMapper {
    fun convert(item: FarmWorkByCropItem) = with(item) {
        CalFarmWorkEntity(
            dreamCrop = DreamCrop.getCropByCode(cropCode),
            startMonth = beginMon,
            startEra = CropScheduleEra.getEraByString(beginEra),
            endMonth = endMon,
            endEra = CropScheduleEra.getEraByString(endEra),
            farmWorkTypeCode = farmWorkTypeCode,
            farmWorkTypeName = farmWorkTypeName,
            farmWork = farmWork,
            videoUrl = vodUrl
        )
    }
}
