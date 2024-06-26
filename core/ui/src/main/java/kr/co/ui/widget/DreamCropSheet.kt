@file:OptIn(ExperimentalMaterial3Api::class)

package kr.co.ui.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kr.co.ui.ext.noRippleClickable
import kr.co.ui.icon.DreamIcon
import kr.co.ui.icon.dreamicon.Garlic
import kr.co.ui.icon.dreamicon.Lettuce
import kr.co.ui.icon.dreamicon.Nappacabbage
import kr.co.ui.icon.dreamicon.Pepper
import kr.co.ui.icon.dreamicon.Potato
import kr.co.ui.icon.dreamicon.Rice
import kr.co.ui.icon.dreamicon.Strawberry
import kr.co.ui.icon.dreamicon.Sunny
import kr.co.ui.icon.dreamicon.Sweetpotato
import kr.co.ui.icon.dreamicon.Tomato
import kr.co.ui.theme.NBDreamTheme
import kr.co.ui.theme.colors
import kr.co.ui.theme.typo

@Composable
fun DreamCropSheet(
    onDismissRequest: () -> Unit,
    selectedList: List<String> = emptyList(),
    onCropClick: (String) -> Unit = {},
) {
    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        dragHandle = null,
    ) {
        Text(
            modifier = Modifier.padding(start = 60.dp, top = 48.dp),
            text = "작물을 골라주세요",
            style = MaterialTheme.typo.h4
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 52.dp,
                    vertical = 32.dp
                ),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Crops.entries.chunked(3).forEach { row ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    row.forEach { crop ->
                        Box(
                            modifier = Modifier
                                .size(60.dp)
                                .minimumInteractiveComponentSize()
                                .background(
                                    color = if (selectedList.contains(crop.value)) MaterialTheme.colors.primary.copy(
                                        0.2f
                                    ) else Color.Transparent,
                                    shape = CircleShape
                                )
                                .clip(CircleShape)
                                .clickable { onCropClick(crop.value) },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                modifier = Modifier.size(40.dp),
                                imageVector = crop.icon,
                                contentDescription = crop.value,
                                tint = Color.Unspecified
                            )
                        }
                    }
                }
            }
        }
    }
}

private enum class Crops(
    val value: String,
    val icon: ImageVector
) {
    PEPPER(
        value = "고추",
        icon = DreamIcon.Pepper
    ),
    RICE(
        value = "벼",
        icon = DreamIcon.Rice,
    ),
    POTATO(
        value = "감자",
        icon = DreamIcon.Potato,
    ),
    SWEET_POTATO(
        value = "고구마",
        icon = DreamIcon.Sweetpotato,
    ),
    APPLE(
        value = "사과",
        icon = DreamIcon.Sunny,
    ),
    STRAWBERRY(
        value = "딸기",
        icon = DreamIcon.Strawberry,
    ),
    GARLIC(
        value = "마늘",
        icon = DreamIcon.Garlic,
    ),
    LETTUCE(
        value = "상추",
        icon = DreamIcon.Lettuce
    ),
    NAPPA_CABBAGE(
        value = "배추",
        icon = DreamIcon.Nappacabbage
    ),
    TOMATO(
        value = "토마토",
        icon = DreamIcon.Tomato
    )
    ;

}

@Preview
@Composable
private fun Preview() {
    var a by remember {
        mutableStateOf(emptyList<String>())
    }
    NBDreamTheme {
        DreamCropSheet(
            selectedList = a,
            onCropClick = { if (a.contains(it)) a = a.filter { s -> s != it } else a + it },
            onDismissRequest = {}
        )
    }
}