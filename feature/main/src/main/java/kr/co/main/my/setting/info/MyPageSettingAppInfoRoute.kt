package kr.co.main.my.setting.info

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kr.co.main.R
import kr.co.ui.ext.scaffoldBackground
import kr.co.ui.theme.NBDreamTheme
import kr.co.ui.theme.colors
import kr.co.ui.theme.typo
import kr.co.ui.widget.DreamCenterTopAppBar

@Composable
internal fun MyPageSettingAppInfoRoute(
    popBackStack: () -> Unit = {},
) {

    MyPageSettingAppInfoScreen(
        popBackStack = popBackStack
    )
}

@Composable
private fun MyPageSettingAppInfoScreen(
    popBackStack: () -> Unit = {},
) {
    Scaffold(
        containerColor = MaterialTheme.colors.white,
        topBar = {
            DreamCenterTopAppBar(
                title = "앱 정보",
                colorBackground = true,
                navigationIcon = {
                    IconButton(
                        onClick = popBackStack
                    ) {
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
                    padding = PaddingValues(0.dp)
                ),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Spacer(modifier = Modifier.height(52.dp))

            ColumnT(
                title = "농부의 꿈"
            )

            ColumnT(
                title = "오픈소스 라이선스",
                description = "오픈소스 소프트웨어에 대한 라이선스 세부정보"
            )

            ColumnT(
                title = "스토어 링크",
                description = "구글 플레이 스토어 링크"
            )

            ColumnT(
                title = "앱 버전",
                description = "1.0.0"
            )
        }
    }
}

@Composable
private fun ColumnT(
    title: String = "",
    description: String? = null,
    onClick: () -> Unit = {},
) {
    Column(
        modifier = Modifier
            .clickable(onClick = onClick)
            .fillMaxWidth()
            .padding(
                horizontal = 24.dp,
                vertical = 12.dp
            ),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typo.body1,
            color = MaterialTheme.colors.gray1
        )

        description?.let {
            Text(
                text = it,
                style = MaterialTheme.typo.label,
                color = MaterialTheme.colors.gray6
            )
        }
    }
}

@Preview
@Composable
private fun Preview() {
    NBDreamTheme {
        MyPageSettingAppInfoScreen()
    }
}
