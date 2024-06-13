package kr.co.main.calendar.providers

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import kr.co.main.calendar.model.DiaryModel

class FakeWorkDescriptionModelListProvider :
    PreviewParameterProvider<List<DiaryModel.WorkDescriptionModel>> {
    override val values = sequenceOf(
        listOf(
            DiaryModel.WorkDescriptionModel(
                id = "0",
                DiaryModel.WorkDescriptionModel.TypeId.WEED.id,
                "감자밭 제초 작업"
            ),
            DiaryModel.WorkDescriptionModel(
                id = "1",
                DiaryModel.WorkDescriptionModel.TypeId.HARVEST.id,
                "봄 감자 수확"
            )
        )
    )
}