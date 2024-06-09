package kr.co.ui.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import kr.co.ui.icon.DreamIcon
import kr.co.ui.icon.dreamicon.Bell
import kr.co.ui.theme.NBDreamTheme
import kr.co.ui.theme.colors
import kr.co.ui.theme.typo

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DreamTopAppBar(
    modifier: Modifier = Modifier,
    title: String,
    colors: TopAppBarColors = TopAppBarDefaults.topAppBarColors(),
    navigationIcon: @Composable () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {},
) {
    TopAppBar(
        modifier = modifier,
        title = {
            Text(
                text = title,
                style = MaterialTheme.typo.h2.copy(
                    color = MaterialTheme.colors.gray1,
                    lineHeight = 30.sp,
                    letterSpacing = (-0.011).em
                )
            )
        },
        colors = colors,
        navigationIcon = navigationIcon,
        actions = {
            actions()
            Spacer(modifier = Modifier.width(12.dp))
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(backgroundColor = 0xFFF0F0F0)
@Composable
private fun DreamTopAppBarPreview() {
    NBDreamTheme {
        DreamTopAppBar(
            title = "내 농장",
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color(0xFFF0F0F0)
            ),
            actions = {
                Icon(
                    imageVector = DreamIcon.Bell,
                    contentDescription = "bell",
                    tint = Color.Unspecified
                )
            }
        )
    }
}