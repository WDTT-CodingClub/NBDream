package kr.co.main.notification

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
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
import kr.co.ui.widget.DreamSwitch

@Composable
internal fun NotificationRoute(
    viewModel: NotificationViewModel = hiltViewModel(),
    popBackStack: () -> Unit = {},
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    NotificationScreen(
        state = state,
        onCheckedNotification = viewModel::onCheckedNotification,
        popBackStack = popBackStack
    )
}

@Composable
private fun NotificationScreen(
    state: NotificationViewModel.State = NotificationViewModel.State(),
    onCheckedNotification: (Boolean) -> Unit = {},
    popBackStack: () -> Unit = {},
) {
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
                        checked = true,
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
                                .background(
                                    color = if (state.selectedTab == it.ordinal) MaterialTheme.colors.gray4 else MaterialTheme.colors.gray8,
                                    shape = RoundedCornerShape(12.dp)
                                )
                                .padding(
                                    horizontal = 16.dp,
                                    vertical = 6.dp
                                ),
                            text = it.value,
                            style = MaterialTheme.typo.body1,
                            color = if (state.selectedTab == it.ordinal) MaterialTheme.colors.white else MaterialTheme.colors.gray4
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    NBDreamTheme {
        NotificationScreen()
    }
}