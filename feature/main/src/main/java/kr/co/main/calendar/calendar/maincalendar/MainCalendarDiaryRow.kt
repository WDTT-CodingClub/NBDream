package kr.co.main.calendar.calendar.maincalendar

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.LayoutScopeMarker
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.ParentDataModifier
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import kr.co.common.util.iterator
import kr.co.main.calendar.common.CalendarDesignToken
import kr.co.main.calendar.model.DiaryModel
import kr.co.ui.icon.DreamIcon
import kr.co.ui.icon.dreamicon.Edit
import java.time.LocalDate
import kotlin.math.max

@RequiresApi(Build.VERSION_CODES.O)
@Composable
internal fun MainCalendarDiaryRow(
    weekStartDate: LocalDate,
    weekEndDate: LocalDate,
    diaries: List<DiaryModel>,
    modifier: Modifier = Modifier
) {
    val content = @Composable {
        for (date in weekStartDate..weekEndDate) {
            diaries.find { it.registerDate == date }?.let{
                DiaryItemScope.MainCalendarDiaryItem(
                    diary = it,
                    onDiaryClick = { _ -> } //TODO 새싹 아이콘 클릭 시, 영농일지 다이얼로그 띄우기
                )
            }
        }
    }

    Layout(
        modifier = modifier,
        content = content,
        measurePolicy = { measurables, constraints ->
            val placeables = measurables.map { measurable ->
                measurable.measure(
                    constraints.copy(maxWidth = constraints.maxWidth / 7)
                )
            }

            var layoutHeight = 0
            placeables.forEach { layoutHeight = max(layoutHeight, it.height) }

            layout(
                width = constraints.maxWidth,
                height = layoutHeight
            ) {
                placeables.forEachIndexed { index, placeable ->
                    placeable.place(
                        x = getXPosition(
                            dayOfWeek = (placeable.parentData as DiaryItemParentData).date.dayOfWeek,
                            constraints = constraints
                        ),
                        y = 0
                    )
                }
            }
        }
    )
}

@Composable
private fun DiaryItemScope.MainCalendarDiaryItem(
    diary: DiaryModel,
    onDiaryClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Icon(
        modifier = modifier
            .fillMaxWidth()
            .diaryItem(diary.registerDate)
            .size(CalendarDesignToken.DIARY_ITEM_ICON_SIZE.dp)
            .clickable {
                onDiaryClick(diary.id)
            },
        imageVector = DreamIcon.Edit, //TODO 새싹 이이콘으로 바꾸기
        contentDescription = ""
    )
}

@LayoutScopeMarker
@Immutable
object DiaryItemScope {
    @Stable
    fun Modifier.diaryItem(date: LocalDate) =
        then(
            DiaryItemParentData(date)
        )
}

private class DiaryItemParentData(
    val date: LocalDate
) : ParentDataModifier {
    override fun Density.modifyParentData(parentData: Any?) = this@DiaryItemParentData
}