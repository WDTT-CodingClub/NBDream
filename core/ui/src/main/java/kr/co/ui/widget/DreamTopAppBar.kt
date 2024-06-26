package kr.co.ui.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kr.co.ui.ext.noRippleClickable
import kr.co.ui.icon.DreamIcon
import kr.co.ui.icon.dreamicon.Bell
import kr.co.ui.theme.NBDreamTheme
import kr.co.ui.theme.colors
import kr.co.ui.theme.typo

@Composable
fun DreamTopAppBar(
    title: String,
    modifier: Modifier = Modifier,
    descriptionAction: (() -> Unit)? = null,
    description: String? = null,
    color: Color = Color.Transparent,
    actions: @Composable RowScope.() -> Unit = {},
) {
    DreamTopAppBar(
        modifier = modifier,
        title = {
            Text(
                text = title,
                style = MaterialTheme.typo.h2
            )
        },
        description = description,
        descriptionAction = descriptionAction,
        color = color,
        actions = actions
    )
}

@Composable
fun DreamTopAppBar(
    title: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    description: String? = null,
    descriptionAction: (() -> Unit)? = null,
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
                title()
                actions()
            }
            description?.let {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .noRippleClickable { descriptionAction?.invoke() },
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = it,
                        style = MaterialTheme.typo.body1,
                        color = MaterialTheme.colors.gray2
                    )

                    descriptionAction?.run {
                        Icon(
                            modifier = Modifier
                                .size(20.dp),
                            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                            contentDescription = null,
                            tint = MaterialTheme.colors.gray2
                        )
                    }
                }
            } ?: Spacer(modifier = Modifier.height(24.dp))
        }
    }
}


@Preview(
    showBackground = true,
    backgroundColor = 0xFFF0F0F0
)
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
            },
            description = "어쩌구 저쩌구"
        )
    }
}