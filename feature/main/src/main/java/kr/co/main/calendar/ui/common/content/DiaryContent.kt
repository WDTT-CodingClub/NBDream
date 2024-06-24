package kr.co.main.calendar.ui.common.content

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import kr.co.common.util.toTitleDateString
import kr.co.main.R
import kr.co.main.calendar.providers.FakeDiaryModelProvider
import kr.co.main.calendar.ui.CalendarDesignToken
import kr.co.main.calendar.model.DiaryModel
import kr.co.main.calendar.model.HolidayModel
import kr.co.ui.icon.DreamIcon
import kr.co.ui.icon.dreamicon.GreenIcon
import kr.co.ui.theme.Paddings
import kr.co.ui.theme.colors
import kr.co.ui.theme.typo
import java.time.LocalDate

private const val HORIZONTAL_DIVIDER_HEIGHT = 0.5

@Composable
internal fun DiaryContent(
    diary: DiaryModel,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        DiaryTitle(
            date = diary.date,
            holidays = diary.holidays
        )
        HorizontalDivider(
            modifier = Modifier.padding(vertical = Paddings.medium),
            color = Color.LightGray,
            thickness = HORIZONTAL_DIVIDER_HEIGHT.dp
        )
        DiaryBody(
            modifier = Modifier.fillMaxWidth(),
            workLaborer = diary.workLaborer,
            workHours = diary.workHours,
            workArea = diary.workArea,
            workDescriptions = diary.workDescriptions
        )
        DiaryImages(
            images = diary.images
        )
        DiaryMemo(
            memo = diary.memo
        )
    }
}

@Composable
private fun DiaryTitle(
    date: LocalDate,
    holidays: List<HolidayModel>,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier) {
        Text(
            modifier = Modifier.padding(end = Paddings.medium),
            text = date.toTitleDateString(),
            style = MaterialTheme.typo.header2M,
            color = MaterialTheme.colors.text1
        )
        holidays
            .sortedBy { it.type.priority }
            .forEach {
                Text(
                    modifier = Modifier.padding(end = Paddings.small),
                    text = it.name,
                    style = MaterialTheme.typo.labelM,
                    color = if (it.isHoliday) Color.Red else MaterialTheme.colors.text2
                )
            }
    }
}

@Composable
private fun DiaryBody(
    workDescriptions: List<DiaryModel.WorkDescriptionModel>,
    modifier: Modifier = Modifier,
    workLaborer: Int = 0,
    workHours: Int = 0,
    workArea: Int = 0
) {
    Column(modifier = modifier) {
        Text(
            modifier = Modifier.align(Alignment.End),
            text = stringResource(
                id = R.string.feature_main_calendar_diary_overview,
                workLaborer, workHours, workArea
            ),
            style = MaterialTheme.typo.labelM,
            color = MaterialTheme.colors.text1
        )
        workDescriptions.forEach {
            DiaryWorkDescription(it)
        }
    }
}

@Composable
private fun DiaryWorkDescription(
    workDescription: DiaryModel.WorkDescriptionModel,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = DreamIcon.GreenIcon,
            contentDescription = ""
        )
        Row(
            modifier = Modifier,
            verticalAlignment = Alignment.Bottom
        ) {
            Text(
                modifier = Modifier.padding(end = Paddings.medium),
                text = workDescription.description,
                style = MaterialTheme.typo.bodyM,
                color = MaterialTheme.colors.text1
            )
            Text(
                text = stringResource(id = workDescription.type.id),
                style = MaterialTheme.typo.labelM,
                color = MaterialTheme.colors.text2
            )
        }
    }
}

@Composable
private fun DiaryImages(
    images: List<String>,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        // TODO 이미지 크기 조정
        for (imageUrl in images) {
            DiaryImage(
                modifier = Modifier
                    .size(CalendarDesignToken.DIARY_IMAGE_SIZE.dp)
                    .clip(RoundedCornerShape(CalendarDesignToken.ROUNDED_CORNER_RADIUS.dp)),
                imageUrl = imageUrl
            )
        }
    }
}

@Composable
private fun DiaryImage(
    imageUrl: String,
    modifier: Modifier = Modifier
) {
    AsyncImage(
        modifier = modifier,
        model = imageUrl,
        contentDescription = "",
        clipToBounds = true
    )
}

@Composable
private fun DiaryMemo(
    memo: String,
    modifier: Modifier = Modifier
) {
    //TODO 메모 2줄 이상인 경우, 더보기  접었다 폈다 할 수 있도록
    if (memo.isNotBlank()) {
        Surface(
            modifier = modifier.padding(top = Paddings.medium),
            shape = RoundedCornerShape(Paddings.xlarge),
            color = Color.LightGray
        ) {
            Text(
                modifier = Modifier.padding(Paddings.medium),
                text = memo,
                style = MaterialTheme.typo.bodyM,
                color = MaterialTheme.colors.text1
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DiaryContentPreview(
    @PreviewParameter(FakeDiaryModelProvider::class) diary: DiaryModel
) {
    DiaryContent(diary = diary)
}