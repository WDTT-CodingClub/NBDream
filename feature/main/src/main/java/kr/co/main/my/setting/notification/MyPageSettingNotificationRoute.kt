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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kr.co.main.R
import kr.co.main.model.my.MyPageNotificationSetting
import kr.co.ui.ext.scaffoldBackground
import kr.co.ui.theme.NBDreamTheme
import kr.co.ui.theme.colors
import kr.co.ui.theme.typo
import kr.co.ui.widget.DreamCenterTopAppBar
import kr.co.ui.widget.DreamSwitch

@Composable
internal fun MyPageSettingNotificationRoute(
    popBackStack: () -> Unit,
    viewModel: MyPageSettingNotificationViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    MyPageSettingNotificationScreen(
        state = state,
        popBackStack = popBackStack
    )
}

@Composable
private fun MyPageSettingNotificationScreen(
    popBackStack: () -> Unit,
    state: MyPageSettingNotificationViewModel.State = MyPageSettingNotificationViewModel.State()
) {
   Scaffold(
       topBar = {
           DreamCenterTopAppBar(
               title = stringResource(id = R.string.feature_main_my_setting_notification),
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
                   group = group
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
    group: MyPageNotificationSetting.Group
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
            MyPageNotificationSetting.getByGroup(
                group
            ).forEach { setting ->
                NotificationRow(stringResource(setting.nameRes))
            }
        }
    }
}

@Composable
private fun NotificationRow(
    text: String,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        val (checked, onCheckedChange) = remember { mutableStateOf(false) }
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
        MyPageSettingNotificationScreen(popBackStack = { /*TODO*/ })
    }
}