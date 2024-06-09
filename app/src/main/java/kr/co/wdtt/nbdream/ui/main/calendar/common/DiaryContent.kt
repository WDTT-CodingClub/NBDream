package kr.co.wdtt.nbdream.ui.main.calendar.common

import android.os.Build
import androidx.annotation.RequiresApi
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import kr.co.wdtt.nbdream.domain.entity.DiaryEntity
import kr.co.wdtt.nbdream.domain.entity.HolidayEntity
import kr.co.wdtt.nbdream.domain.entity.WorkDescriptionEntity
import kr.co.wdtt.nbdream.ui.icon.dreamicon.DreamIcon
import kr.co.wdtt.nbdream.ui.icon.dreamicon.Sprout
import kr.co.wdtt.nbdream.ui.main.calendar.providers.FakeDiaryEntityProvider
import kr.co.wdtt.nbdream.ui.theme.Paddings
import kr.co.wdtt.nbdream.ui.theme.colors
import kr.co.wdtt.nbdream.ui.theme.typo
import kr.co.wdtt.nbdream.ui.util.toTitleDateString
import java.time.LocalDate

private const val HORIZONTAL_DIVIDER_HEIGHT = 0.5
private const val IMAGE_SCALE = 120

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DiaryContent(
    diary: DiaryEntity,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        DiaryTitle(
            date = diary.registerDate,
            holidays = diary.holidays
        )
        CalendarWeather(
            weatherForecast = diary.weatherForecast
        )
        HorizontalDivider(
            color = Color.LightGray,
            thickness = HORIZONTAL_DIVIDER_HEIGHT.dp,
            modifier = Modifier.padding(vertical = Paddings.medium)
        )
        DiaryBody(
            workLaborer = diary.workLaborer,
            workHours = diary.workHours,
            workArea = diary.workArea,
            workDescriptionEntities = diary.workDescriptionEntities
        )
        DiaryImages(
            images = diary.images
        )
        DiaryMemo(
            memo = diary.memo
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun DiaryTitle(
    date: LocalDate,
    holidays: List<HolidayEntity>,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier) {
        Text(
            modifier = Modifier.padding(end = Paddings.medium),
            text = date.toTitleDateString(),
            style = MaterialTheme.typo.header2M,
            color = MaterialTheme.colors.text1
        )
        // TODO 휴일 중요도 순으로 정렬
        holidays.forEach {
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
    workLaborer: Int,
    workHours: Int,
    workArea: Int,
    workDescriptionEntities: List<WorkDescriptionEntity>,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            modifier = Modifier.align(Alignment.End),
            text = "${workLaborer}명/${workHours}시간/${workArea}평",
            style = MaterialTheme.typo.labelM,
            color = MaterialTheme.colors.text1
        )
        workDescriptionEntities.forEach {
            DiaryWorkDescription(it)
        }
    }
}

@Composable
private fun DiaryWorkDescription(
    workDescriptionEntity: WorkDescriptionEntity,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = DreamIcon.Sprout,
            contentDescription = ""
        )
        Row(
            modifier = Modifier,
            verticalAlignment = Alignment.Bottom
        ) {
            Text(
                modifier = Modifier.padding(end = Paddings.medium),
                text = workDescriptionEntity.description,
                style = MaterialTheme.typo.bodyM,
                color = MaterialTheme.colors.text1
            )
            Text(
                text = stringResource(id = workDescriptionEntity.type.labelId),
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
        // TODO 이미지 크기 확인, 이미지 모서리 둥글게
        images.forEach {
            AsyncImage(
                modifier = Modifier.size(IMAGE_SCALE.dp),
                model = it,
                contentDescription = "",
                clipToBounds = true
            )
        }
    }
}

@Composable
private fun DiaryMemo(
    memo: String,
    modifier: Modifier = Modifier
) {
    //TODO 메모 2줄 이상인 경우, 더보기로 접었다 폈다 할 수 있도록
    if (!memo.isNullOrBlank()) {
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

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
private fun DiaryContentPreview(
    @PreviewParameter(FakeDiaryEntityProvider::class) diary: DiaryEntity
) {
    DiaryContent(diary = diary)
}