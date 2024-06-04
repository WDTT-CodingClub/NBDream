package kr.co.wdtt.nbdream.ui.main.calendar.providers

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import kr.co.wdtt.nbdream.domain.entity.DreamCrop
import kr.co.wdtt.nbdream.domain.entity.FarmWorkCategory
import kr.co.wdtt.nbdream.domain.entity.FarmWorkEntity
import kr.co.wdtt.nbdream.domain.entity.FarmWorkEra

class FakeFarmWorkEntityListProvider : PreviewParameterProvider<List<FarmWorkEntity>>{
    override val values = sequenceOf(
        listOf(
            FarmWorkEntity(
                id = "1",
                dreamCrop = DreamCrop.POTATO,
                startEra = FarmWorkEra.EARLY,
                endEra = FarmWorkEra.EARLY,
                category = FarmWorkCategory.GROWTH,
                farmWork = "경엽신장기",
                videoUrl = ""
            ),
            FarmWorkEntity(
                id = "2",
                dreamCrop = DreamCrop.POTATO,
                startEra = FarmWorkEra.MID,
                endEra = FarmWorkEra.LATE,
                category = FarmWorkCategory.GROWTH,
                farmWork = "배수, 병해충방제",
                videoUrl = ""
            ),
            FarmWorkEntity(
                id = "3",
                dreamCrop = DreamCrop.POTATO,
                startEra = FarmWorkEra.LATE,
                endEra = FarmWorkEra.LATE,
                category = FarmWorkCategory.GROWTH,
                farmWork = "덩이줄기비대기",
                videoUrl = ""
            ),
            FarmWorkEntity(
                id = "4",
                dreamCrop = DreamCrop.POTATO,
                startEra = FarmWorkEra.EARLY,
                endEra = FarmWorkEra.LATE,
                category = FarmWorkCategory.CLIMATE,
                farmWork = "가뭄",
                videoUrl = ""
            ),
            FarmWorkEntity(
                id = "5",
                dreamCrop = DreamCrop.POTATO,
                startEra = FarmWorkEra.EARLY,
                endEra = FarmWorkEra.LATE,
                category = FarmWorkCategory.CLIMATE,
                farmWork = "진딧물다발 덩이줄기 비대불량",
                videoUrl = ""
            ),
            FarmWorkEntity(
                id = "6",
                dreamCrop = DreamCrop.POTATO,
                startEra = FarmWorkEra.EARLY,
                endEra = FarmWorkEra.LATE,
                category = FarmWorkCategory.PEST,
                farmWork = "진딧물(바이러스병)",
                videoUrl = ""
            )
        )
    )
    override val count: Int = values.count()
}