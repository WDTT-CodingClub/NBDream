package kr.co.main.my.setting

import android.content.pm.ApplicationInfo
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kr.co.main.BuildConfig
import kr.co.main.R
import kr.co.main.model.my.MyPageSetting
import kr.co.ui.ext.noRippleClickable
import kr.co.ui.ext.scaffoldBackground
import kr.co.ui.theme.NBDreamTheme
import kr.co.ui.theme.colors
import kr.co.ui.theme.typo
import kr.co.ui.widget.DreamCenterTopAppBar

@Composable
internal fun MyPageSettingRoute(
    popBackStack: () -> Unit,
) {

    MyPageSettingScreen(
        popBackStack = popBackStack
    )
}

@Composable
private fun MyPageSettingScreen(
    popBackStack: () -> Unit,
) {
    Scaffold(
        topBar = {
            DreamCenterTopAppBar(
                title = "설정",
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
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            MyPageSetting.entries.forEachIndexed { index, myPageSetting ->
                SettingRow(
                    type = myPageSetting,
                )

                if(index != MyPageSetting.entries.lastIndex){
                    HorizontalDivider(
                        thickness = 1.dp,
                        color = MaterialTheme.colors.gray8
                    )
                }
            }

            Text(text = "버전: 1.0.0")
        }
    }
}

@Composable
private fun SettingRow(
    type: MyPageSetting
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .noRippleClickable { },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = stringResource(id = type.resId),
            style = MaterialTheme.typo.body1,
            color = MaterialTheme.colors.gray1,
        )

        Icon(
            modifier = Modifier.size(20.dp),
            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
            contentDescription = stringResource(id = type.resId),
            tint = MaterialTheme.colors.gray5
        )
    }
}

@Preview
@Composable
private fun Preview() {
    NBDreamTheme {
        MyPageSettingScreen{

        }
    }
}