package kr.co.main.calendar.common.content

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import kr.co.common.util.format
import kr.co.main.R
import kr.co.main.model.calendar.DiaryModel
import kr.co.main.model.calendar.HolidayModel
import kr.co.main.providers.calendar.FakeDiaryModelProvider
import kr.co.ui.icon.DreamIcon
import kr.co.ui.icon.dreamicon.GreenIcon
import kr.co.ui.theme.NBDreamTheme
import kr.co.ui.theme.Paddings
import kr.co.ui.theme.colors
import kr.co.ui.theme.typo
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale

private const val HORIZONTAL_DIVIDER_HEIGHT = 0.5

@Composable
internal fun DiaryContent(
    diary: DiaryModel,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
    ) {
        DiaryTitle(
            registerDate = diary.date,
            holidays = diary.holidays
        )

        Spacer(modifier = Modifier.height(3.dp))

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
    registerDate: LocalDate,
    holidays: List<HolidayModel>,
    modifier: Modifier = Modifier,
    isToday: Boolean = false,
) {
    Row(modifier = modifier) {
        Text(
            text = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        fontFamily = MaterialTheme.typo.h4.fontFamily,
                        fontSize = MaterialTheme.typo.h4.fontSize,
                        fontStyle = MaterialTheme.typo.h4.fontStyle,
                        fontWeight = MaterialTheme.typo.h4.fontWeight,
                        letterSpacing = MaterialTheme.typo.h4.letterSpacing,
                        color = MaterialTheme.colors.black
                    )
                ) {
                    append(
                        registerDate.format("MM월dd일")
                            + registerDate.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.KOREAN)
                    )
                }
                withStyle(
                    style = SpanStyle(
                        color = Color(0xFF27A853)
                )
                ) {
                    if (!isToday) append("  오늘")
                }
                append("  " + (holidays.firstOrNull()?.name?:""))
            },
            style = MaterialTheme.typo.body1.copy(
                color = MaterialTheme.colors.black.copy(0.4f)
            )
        )
    }
}

@Composable
private fun DiaryBody(
    workDescriptions: List<DiaryModel.WorkDescriptionModel>,
    modifier: Modifier = Modifier,
    workLaborer: Int = 0,
    workHours: Int = 0,
    workArea: Int = 0,
) {
    Column(modifier = modifier) {
        Text(
            text = stringResource(
                id = R.string.feature_main_calendar_diary_work_laborer_hour_area,
                workLaborer, workHours, workArea
            ),
            style = MaterialTheme.typo.body2,
            color = MaterialTheme.colors.gray5
        )

        Spacer(modifier = Modifier.height(18.dp))

        workDescriptions.forEach {
            DiaryWorkDescription(it)

            Spacer(modifier = Modifier.height(4.dp))
        }
    }
}

@Composable
private fun DiaryWorkDescription(
    workDescription: DiaryModel.WorkDescriptionModel,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Icon(
            imageVector = DreamIcon.GreenIcon,
            contentDescription = "",
            tint = Color.Unspecified
        )
        Text(
            text = workDescription.description,
            style = MaterialTheme.typo.body1,
            color = MaterialTheme.colors.gray1
        )
        Text(
            text = stringResource(id = workDescription.type.id),
            style = MaterialTheme.typo.body1,
            color = MaterialTheme.colors.gray5
        )
    }
}

@Composable
private fun DiaryImages(
    images: List<String>,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        // TODO 이미지 크기 조정
        for (imageUrl in images) {
            DiaryImage(
                modifier = Modifier
                    .size(kr.co.main.calendar.CalendarDesignToken.DIARY_IMAGE_SIZE.dp)
                    .clip(RoundedCornerShape(kr.co.main.calendar.CalendarDesignToken.ROUNDED_CORNER_RADIUS.dp)),
                imageUrl = imageUrl
            )
        }
    }
}

@Composable
private fun DiaryImage(
    imageUrl: String,
    modifier: Modifier = Modifier,
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
    modifier: Modifier = Modifier,
) {
    //TODO 메모 2줄 이상인 경우, 더보기  접었다 폈다 할 수 있도록
    if (memo.isNotBlank()) {
        Surface(
            modifier = modifier.padding(top = Paddings.medium),
            shape = RoundedCornerShape(Paddings.xlarge),
            color = Color.Transparent
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
    @PreviewParameter(FakeDiaryModelProvider::class) diary: DiaryModel,
) {
    NBDreamTheme {
        DiaryContent(diary = diary)
    }
}