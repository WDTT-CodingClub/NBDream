package kr.co.main.my.setting.notification

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kr.co.main.R
import kr.co.main.model.my.MyPageNotificationSetting
import kr.co.main.notification.NotificationViewModel
import kr.co.main.notification.openAppSettings
import kr.co.ui.ext.scaffoldBackground
import kr.co.ui.theme.NBDreamTheme
import kr.co.ui.theme.colors
import kr.co.ui.theme.typo
import kr.co.ui.widget.DreamCenterTopAppBar
import kr.co.ui.widget.DreamDialog
import kr.co.ui.widget.DreamSwitch

@Composable
internal fun MyPageSettingNotificationRoute(
    popBackStack: () -> Unit,
    viewModel: MyPageSettingNotificationViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.navigationEffects.collect { effect ->
            when (effect) {
                NotificationViewModel.NotificationViewEffect.NavigateToAppSettings -> context.openAppSettings()
            }
        }
    }

    MyPageSettingNotificationScreen(
        state = state,
        popBackStack = popBackStack,
        onCommentAlarmChanged = viewModel::updateCommentAlarm,
        onScheduleAlarmChanged = viewModel::updateScheduleAlarm
    )

    if (state.showPermissionDialog) {
        DreamDialog(
            header = "권한 설정 필요",
            description = "알림을 받기 위해서는 권한을 설정해야 합니다.",
            onConfirm = viewModel::onPermissionGrantedClick,
            onDismissRequest = viewModel::onPermissionDialogDismiss
        )
    }
}

@Composable
private fun MyPageSettingNotificationScreen(
    popBackStack: () -> Unit,
    state: MyPageSettingNotificationViewModel.State,
    onCommentAlarmChanged: (Boolean) -> Unit,
    onScheduleAlarmChanged: (Boolean) -> Unit
) {
    Scaffold(
        containerColor = MaterialTheme.colors.white,
        topBar = {
            DreamCenterTopAppBar(
                title = stringResource(id = R.string.feature_main_my_setting_notification),
                colorBackground = true,
                navigationIcon = {
                    IconButton(onClick = popBackStack) {
                        Icon(
                            modifier = Modifier.size(32.dp),
                            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                            contentDescription = stringResource(id = R.string.feature_main_pop_back_stack)
                        )
                    }
                }
            )
        }
    ) { scaffoldPadding ->
        Column(
            modifier = Modifier
                .scaffoldBackground(
                    scaffoldPadding = scaffoldPadding,
                    padding = PaddingValues(horizontal = 24.dp)
                )
                .padding(top = 52.dp),
            verticalArrangement = Arrangement.spacedBy(32.dp)
        ) {
            MyPageNotificationSetting.Group.entries.forEachIndexed { index, group ->
                NotificationColumn(
                    group = group,
                    state = state,
                    onCommentAlarmChanged = onCommentAlarmChanged,
                    onScheduleAlarmChanged = onScheduleAlarmChanged
                )
                if (index != MyPageNotificationSetting.Group.entries.lastIndex)
                    HorizontalDivider(
                        thickness = 1.dp,
                        color = MaterialTheme.colors.gray8
                    )
            }
        }
    }
}

@Composable
private fun NotificationColumn(
    group: MyPageNotificationSetting.Group,
    state: MyPageSettingNotificationViewModel.State,
    onCommentAlarmChanged: (Boolean) -> Unit,
    onScheduleAlarmChanged: (Boolean) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(32.dp)
    ) {
        Text(
            text = group.text,
            style = MaterialTheme.typo.h4,
            color = MaterialTheme.colors.gray1
        )

        Column(
            modifier = Modifier.fillMaxWidth(),
        ) {
            MyPageNotificationSetting.getByGroup(group).forEach { setting ->
                val checked = when (setting) {
                    MyPageNotificationSetting.COMMENT -> state.commentAlarm ?: false
                    MyPageNotificationSetting.SCHEDULE -> state.scheduleAlarm ?: false
                }
                NotificationRow(
                    text = stringResource(setting.nameRes),
                    checked = checked,
                    onCheckedChange = {
                        when (setting) {
                            MyPageNotificationSetting.COMMENT -> onCommentAlarmChanged(it)
                            MyPageNotificationSetting.SCHEDULE -> onScheduleAlarmChanged(it)
                        }
                    }
                )
            }
        }
    }
}

@Composable
private fun NotificationRow(
    text: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = text,
            style = MaterialTheme.typo.body1,
            color = MaterialTheme.colors.gray1
        )

        DreamSwitch(
            checked = checked,
            onCheckedChange = onCheckedChange
        )
    }
}

@Preview
@Composable
private fun Preview() {
    NBDreamTheme {
        val state = MyPageSettingNotificationViewModel.State(
            commentAlarm = true,
            scheduleAlarm = false
        )

        MyPageSettingNotificationScreen(
            state = state,
            popBackStack = { /*TODO*/ },
            onCommentAlarmChanged = { /*TODO*/ },
            onScheduleAlarmChanged = { /*TODO*/ })
    }
}