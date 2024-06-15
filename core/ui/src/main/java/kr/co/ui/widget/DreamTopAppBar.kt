package kr.co.ui.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kr.co.ui.icon.DreamIcon
import kr.co.ui.icon.dreamicon.Bell
import kr.co.ui.theme.NBDreamTheme
import kr.co.ui.theme.colors
import kr.co.ui.theme.typo

@Composable
fun DreamTopAppBar(
    modifier: Modifier = Modifier,
    title: String,
    description: String? = null,
    color: Color = Color.Transparent,
    actions: @Composable RowScope.() -> Unit = {},
) {
    Column {
        Spacer(modifier = Modifier.height(24.dp))
        Column(
            modifier = modifier
                .fillMaxWidth()
                .background(color = color),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typo.h2
                )

                actions()
            }
            description?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typo.body1,
                    color = MaterialTheme.colors.gray2
                )
            }?: Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(backgroundColor = 0xFFF0F0F0)
@Composable
private fun DreamTopAppBarPreview() {
    NBDreamTheme {
        DreamTopAppBar(
            title = "내 농장",
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