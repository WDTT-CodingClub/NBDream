package kr.co.main.notification

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kr.co.main.R
import kr.co.main.model.notification.NotificationTab
import kr.co.ui.ext.scaffoldBackground
import kr.co.ui.theme.NBDreamTheme
import kr.co.ui.theme.colors
import kr.co.ui.theme.typo
import kr.co.ui.widget.DreamCenterTopAppBar
import kr.co.ui.widget.DreamDialog
import kr.co.ui.widget.DreamSwitch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit

@Composable
internal fun NotificationRoute(
    viewModel: NotificationViewModel = hiltViewModel(),
    popBackStack: () -> Unit = {},
    navigationToSchedule: () -> Unit,
    navigationToCommunity: (Long?) -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    var isShowDeleteDialog by remember { mutableStateOf(false) }

    NotificationScreen(
        state = state,
        onCheckedNotification = viewModel::onCheckedNotification,
        popBackStack = popBackStack,
        navigationToSchedule = navigationToSchedule,
        navigationToCommunity = navigationToCommunity,
        onTabSelected = viewModel::onTabSelected,
        onDeleteClicked = viewModel::onDeleteClicked,
        isShowDeleteDialog = { isShowDeleteDialog = it }
    )

    if (isShowDeleteDialog) {
        DreamDialog(
            header = "모든 알림 삭제",
            description = "모든 알림을 삭제 하시겠습니까?",
            onConfirm = viewModel::onDeleteClicked,
            onDismissRequest = { isShowDeleteDialog = false }
        )
    }
}

@Composable
private fun NotificationScreen(
    state: NotificationViewModel.State = NotificationViewModel.State(),
    onCheckedNotification: (Boolean) -> Unit = {},
    onTabSelected: (Int) -> Unit = {},
    popBackStack: () -> Unit = {},
    navigationToSchedule: () -> Unit,
    navigationToCommunity: (Long?) -> Unit,
    onDeleteClicked: (Long?) -> Unit = {},
    isShowDeleteDialog: (Boolean) -> Unit
) {
    val filteredAlarmHistories = when (NotificationTab.entries[state.selectedTab]) {
        NotificationTab.TOTAL -> state.alarmHistories
        NotificationTab.SCHEDULE -> state.alarmHistories.filter { it.alarmType == "schedule" }
        NotificationTab.COMMUNITY -> state.alarmHistories.filter { it.alarmType == "comment" }
    }
    var isEditMode by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            DreamCenterTopAppBar(
                title = "알림",
                navigationIcon = {
                    IconButton(onClick = popBackStack) {
                        Icon(
                            modifier = Modifier.size(32.dp),
                            imageVector = Icons.AutoMirrored.Default.KeyboardArrowLeft,
                            contentDescription = stringResource(R.string.feature_main_pop_back_stack)
                        )
                    }
                },
            )
        }
    ) { scaffoldPadding ->
        LazyColumn(
            modifier = Modifier
                .scaffoldBackground(
                    scaffoldPadding = scaffoldPadding,
                    padding = PaddingValues(0.dp)
                )
        ) {
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = MaterialTheme.colors.green8)
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "일정/커뮤니티 소식을 받아보세요!",
                        style = MaterialTheme.typo.body1.copy(
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Normal,
                            color = MaterialTheme.colors.gray1
                        )
                    )

                    Spacer(modifier = Modifier.width(42.dp))

                    Text(
                        text = "알림 받기",
                        style = MaterialTheme.typo.body1.copy(
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Normal,
                            color = MaterialTheme.colors.gray1
                        )
                    )

                    Spacer(modifier = Modifier.width(16.dp))

                    DreamSwitch(
                        modifier = Modifier
                            .size(
                                width = 48.dp,
                                height = 24.dp
                            ),
                        checked = state.setting,
                        onCheckedChange = onCheckedNotification
                    )
                }
            }

            item {
                Spacer(modifier = Modifier.height(32.dp))
                LazyRow(
                    modifier = Modifier.padding(start = 18.5.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(NotificationTab.entries) {
                        Text(
                            modifier = Modifier
                                .clickable { onTabSelected(it.ordinal) }
                                .background(
                                    color = if (state.selectedTab == it.ordinal) MaterialTheme.colors.gray4 else MaterialTheme.colors.gray8,
                                    shape = RoundedCornerShape(12.dp)
                                )
                                .padding(
                                    horizontal = 16.dp,
                                    vertical = 6.dp
                                )
                                .semantics {
                                    contentDescription = "${it.value} 알림 탭"
                                },
                            text = it.value,
                            style = MaterialTheme.typo.body1,
                            color = if (state.selectedTab == it.ordinal) MaterialTheme.colors.white else MaterialTheme.colors.gray4
                        )
                    }
                }
            }

            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (isEditMode) {
                        Text(
                            modifier = Modifier.clickable {
                                isShowDeleteDialog(true)
                            },
                            text = "전체 삭제",
                            style = MaterialTheme.typo.body1.copy(
                                color = MaterialTheme.colors.gray1
                            )
                        )
                    } else {
                        Spacer(modifier = Modifier.width(42.dp))
                    }
                    Text(
                        modifier = Modifier.clickable {
                            isEditMode = !isEditMode
                        },
                        text = if (isEditMode) "완료" else "편집",
                        style = MaterialTheme.typo.body1.copy(
                            color = MaterialTheme.colors.gray1
                        )
                    )
                }
            }

            items(filteredAlarmHistories) { alarmHistory ->
                when (alarmHistory.alarmType) {
                    "comment" -> AlarmCommentCard(
                        alarmHistory = alarmHistory,
                        onItemClicked = { id ->
                            if (isEditMode) {
                                onDeleteClicked(id)
                            } else {
                                navigationToCommunity(id)
                            }
                        },
                        isEditMode = isEditMode
                    )

                    "schedule" -> AlarmScheduleCard(
                        alarmHistory = alarmHistory,
                        onItemClicked = { id ->
                            if (isEditMode) {
                                onDeleteClicked(id)
                            } else {
                                navigationToSchedule()
                            }
                        },
                        isEditMode = isEditMode
                    )
                }
            }
        }
    }
}

@Composable
private fun AlarmScheduleCard(
    alarmHistory: NotificationViewModel.State.AlarmHistory,
    onItemClicked: (Long) -> Unit,
    isEditMode: Boolean
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable {
                if (!isEditMode) onItemClicked(alarmHistory.targetId)
            },
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        if (isEditMode) {
            IconButton(onClick = {
                onItemClicked(alarmHistory.id)
            }) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = null,
                    tint = MaterialTheme.colors.gray5
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
        }
        Image(
            painter = painterResource(kr.co.nbdream.core.ui.R.drawable.img_default_profile),
            contentDescription = null,
            modifier = Modifier.size(40.dp)
        )
        Spacer(modifier = Modifier.width(20.dp))
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = alarmHistory.title,
                style = MaterialTheme.typo.body1.copy(
                    color = MaterialTheme.colors.gray1
                )
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = alarmHistory.content,
                style = MaterialTheme.typo.body1.copy(
                    color = MaterialTheme.colors.gray1
                )
            )
        }
        Spacer(modifier = Modifier.width(20.dp))
        Text(
            text = formatRelativeDate(alarmHistory.createdDate),
            style = MaterialTheme.typo.label.copy(
                color = MaterialTheme.colors.gray5
            )
        )
    }
}

@Composable
private fun AlarmCommentCard(
    alarmHistory: NotificationViewModel.State.AlarmHistory,
    onItemClicked: (Long) -> Unit,
    isEditMode: Boolean
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable {
                if (!isEditMode) onItemClicked(alarmHistory.targetId)
            },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        if (isEditMode) {
            IconButton(onClick = {
                onItemClicked(alarmHistory.id)
            }) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = null,
                    tint = MaterialTheme.colors.gray5
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
        }
        Image(
            painter = painterResource(kr.co.nbdream.core.ui.R.drawable.img_default_profile),
            contentDescription = null,
            modifier = Modifier.size(40.dp)
        )
        Spacer(modifier = Modifier.width(20.dp))
        Text(
            modifier = Modifier.weight(1f),
            text = alarmHistory.content,
            style = MaterialTheme.typo.body1.copy(
                color = MaterialTheme.colors.gray1
            )
        )
        Spacer(modifier = Modifier.width(20.dp))
        Text(
            text = formatRelativeDate(alarmHistory.createdDate),
            style = MaterialTheme.typo.label.copy(
                color = MaterialTheme.colors.gray5
            )
        )
    }
}

private fun formatRelativeDate(createdDate: String): String {
    val currentDate = Date()
    val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
    val created = dateFormat.parse(createdDate) ?: return createdDate

    val diffInMillis = currentDate.time - created.time
    val diffInMinutes = TimeUnit.MILLISECONDS.toMinutes(diffInMillis)
    val diffInHours = TimeUnit.MILLISECONDS.toHours(diffInMillis)
    val diffInDays = TimeUnit.MILLISECONDS.toDays(diffInMillis)

    return when {
        diffInMinutes < 60 -> "${diffInMinutes}분전"
        diffInHours < 24 -> "${diffInHours}시간전"
        diffInDays < 1 -> "1일전"
        else -> SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(created)
    }
}

@Preview
@Composable
private fun Preview() {
    NBDreamTheme {
        NotificationScreen(
            navigationToSchedule = {},
            navigationToCommunity = {},
            isShowDeleteDialog = {}
        )
    }
}