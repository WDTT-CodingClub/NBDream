package kr.co.main.calendar.providers

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import kr.co.domain.entity.FarmWorkEntity
import kr.co.main.calendar.model.CropModel
import kr.co.main.calendar.model.FarmWorkModel

internal class FakeFarmWorkModelListProvider : PreviewParameterProvider<List<FarmWorkModel>> {
    override val values = sequenceOf(
        listOf(
            FarmWorkModel(
                id = "1",
                crop = CropModel.POTATO,
                startEra = FarmWorkEntity.Era.EARLY,
                endEra = FarmWorkEntity.Era.EARLY,
                category = FarmWorkEntity.Category.GROWTH,
                farmWork = "경엽신장기",
                videoUrl = ""
            ),
            FarmWorkModel(
                id = "2",
                crop = CropModel.POTATO,
                startEra = FarmWorkEntity.Era.MID,
                endEra = FarmWorkEntity.Era.LATE,
                category = FarmWorkEntity.Category.GROWTH,
                farmWork = "배수, 병해충방제",
                videoUrl = ""
            ),
            FarmWorkModel(
                id = "3",
                crop = CropModel.POTATO,
                startEra = FarmWorkEntity.Era.LATE,
                endEra = FarmWorkEntity.Era.LATE,
                category = FarmWorkEntity.Category.GROWTH,
                farmWork = "덩이줄기비대기",
                videoUrl = ""
            ),
            FarmWorkModel
                (
                id = "4",
                crop = CropModel.POTATO,
                startEra = FarmWorkEntity.Era.EARLY,
                endEra = FarmWorkEntity.Era.LATE,
                category = FarmWorkEntity.Category.CLIMATE,
                farmWork = "가뭄",
                videoUrl = ""
            ),
            FarmWorkModel
                (
                id = "5",
                crop = CropModel.POTATO,
                startEra = FarmWorkEntity.Era.EARLY,
                endEra = FarmWorkEntity.Era.LATE,
                category = FarmWorkEntity.Category.CLIMATE,
                farmWork = "진딧물다발 덩이줄기 비대불량",
                videoUrl = ""
            ),
            FarmWorkModel
                (
                id = "6",
                crop = CropModel.POTATO,
                startEra = FarmWorkEntity.Era.EARLY,
                endEra = FarmWorkEntity.Era.LATE,
                category = FarmWorkEntity.Category.PEST,
                farmWork = "진딧물(바이러스병)",
                videoUrl = ""
            )
        )
    )
    override val count: Int = values.count()
}