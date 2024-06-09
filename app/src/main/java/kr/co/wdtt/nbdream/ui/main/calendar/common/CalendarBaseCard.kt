package kr.co.wdtt.nbdream.ui.main.calendar.common

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.zIndex
import kr.co.wdtt.nbdream.R
import kr.co.wdtt.nbdream.domain.entity.DiaryEntity
import kr.co.wdtt.nbdream.domain.entity.ScheduleEntity
import kr.co.wdtt.nbdream.ui.icon.dreamicon.Delete
import kr.co.wdtt.nbdream.ui.icon.dreamicon.DreamIcon
import kr.co.wdtt.nbdream.ui.icon.dreamicon.Dropdown
import kr.co.wdtt.nbdream.ui.icon.dreamicon.Edit
import kr.co.wdtt.nbdream.ui.main.calendar.providers.FakeDiaryEntityProvider
import kr.co.wdtt.nbdream.ui.main.calendar.providers.FakeScheduleEntityProvider
import kr.co.wdtt.nbdream.ui.theme.Paddings
import kr.co.wdtt.nbdream.ui.theme.typo

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CalendarBaseCard(
    calendarContent: CalendarContent,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(Paddings.xlarge)
    ) {
        Box(modifier = Modifier.padding(Paddings.xlarge)) {
            //TODO 일정 제목 길어졌을 때 드롭다운 아이콘과 겹쳐지지 않도록 하기

            CardDropDownMenu(
                onEdit = { /*TODO*/ },
                onDelete = { /*TODO*/ },
                modifier = Modifier.align(Alignment.TopEnd)
            )
            CalendarContentWrapper(
                calendarContent = calendarContent,
                modifier = Modifier.align(Alignment.TopStart)
            )
        }
    }
}

@Composable
private fun CardDropDownMenu(
    onEdit: () -> Unit,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier
) {
    var expandDropDown by remember { mutableStateOf(false) }

    Row(modifier = modifier) {
        Icon(
            modifier = Modifier.clickable { expandDropDown = true },
            imageVector = DreamIcon.Dropdown,
            contentDescription = ""
        )
        // TODO 드롭다운 메뉴 expanded 되었을 때 드롭다운 아이템 보이는지 확인
        // TODO 드롭다운 아이템 목록 아이콘 아래에 오도록 위치 조정
        DropdownMenu(
            expanded = expandDropDown,
            //expanded = true,
            onDismissRequest = { expandDropDown = false },
        ) {
            CardDropDownMenuItem(
                modifier = Modifier.zIndex(1f),
                menuIcon = DreamIcon.Edit,
                menuNameId = R.string.dropdown_menu_edit,
                onClick = onEdit
            )
            CardDropDownMenuItem(
                modifier = Modifier.zIndex(1f),
                menuIcon = DreamIcon.Delete,
                menuNameId = R.string.dropdown_menu_delete,
                onClick = onDelete
            )
        }
    }
}

@Composable
private fun CardDropDownMenuItem(
    menuIcon: ImageVector,
    @StringRes menuNameId: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.padding(end = Paddings.xlarge),
            text = stringResource(id = menuNameId),
            style = MaterialTheme.typo.bodyM
        )
        Icon(
            imageVector = menuIcon,
            contentDescription = "",
        )
    }
}

@Preview
@Composable
private fun CardDropDownMenuItemPreview() {
    CardDropDownMenuItem(
        menuIcon = DreamIcon.Delete,
        menuNameId = R.string.dropdown_menu_delete,
        onClick = { /*TODO*/ }
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
private fun ScheduleCardPreview(
    @PreviewParameter(FakeScheduleEntityProvider::class) schedule: ScheduleEntity
) {
    CalendarBaseCard(calendarContent = CalendarContent.create(schedule))
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
private fun DiaryCardPreview(
    @PreviewParameter(FakeDiaryEntityProvider::class) diary: DiaryEntity
) {
    CalendarBaseCard(calendarContent = CalendarContent.create(diary))
}
