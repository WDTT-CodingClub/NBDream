package kr.co.main.calendar.providers

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import kr.co.domain.entity.FarmWorkEntity

/**
 * Provider 기본이 시퀀스로 되어 있는데, 다시 리스트로 감싸서 넣어줄 필욘 없을 것 같습니다.
 * @param List :목록 데이터가 전부 들어가야 사용가능
 * @param Sequence : 들어올때마다 순서대로 사용가능
 */
class FakeFarmWorkEntityListProvider : PreviewParameterProvider<FarmWorkEntity>{
    override val values = sequenceOf<FarmWorkEntity>(
//            FarmWorkEntity(
//                id = "1",
//                dreamCrop = DreamCrop.POTATO,
//                startEra = FarmWorkEra.EARLY,
//                endEra = FarmWorkEra.EARLY,
//                category = FarmWorkCategory.GROWTH,
//                farmWork = "경엽신장기",
//                videoUrl = ""
//            ),
//            FarmWorkEntity(
//                id = "2",
//                dreamCrop = DreamCrop.POTATO,
//                startEra = FarmWorkEra.MID,
//                endEra = FarmWorkEra.LATE,
//                category = FarmWorkCategory.GROWTH,
//                farmWork = "배수, 병해충방제",
//                videoUrl = ""
//            ),
//            FarmWorkEntity(
//                id = "3",
//                dreamCrop = DreamCrop.POTATO,
//                startEra = FarmWorkEra.LATE,
//                endEra = FarmWorkEra.LATE,
//                category = FarmWorkCategory.GROWTH,
//                farmWork = "덩이줄기비대기",
//                videoUrl = ""
//            ),
//            FarmWorkEntity(
//                id = "4",
//                dreamCrop = DreamCrop.POTATO,
//                startEra = FarmWorkEra.EARLY,
//                endEra = FarmWorkEra.LATE,
//                category = FarmWorkCategory.CLIMATE,
//                farmWork = "가뭄",
//                videoUrl = ""
//            ),
//            FarmWorkEntity(
//                id = "5",
//                dreamCrop = DreamCrop.POTATO,
//                startEra = FarmWorkEra.EARLY,
//                endEra = FarmWorkEra.LATE,
//                category = FarmWorkCategory.CLIMATE,
//                farmWork = "진딧물다발 덩이줄기 비대불량",
//                videoUrl = ""
//            ),
//            FarmWorkEntity(
//                id = "6",
//                dreamCrop = DreamCrop.POTATO,
//                startEra = FarmWorkEra.EARLY,
//                endEra = FarmWorkEra.LATE,
//                category = FarmWorkCategory.PEST,
//                farmWork = "진딧물(바이러스병)",
//                videoUrl = ""
//            )
    )
    override val count: Int = values.count()
}