package kr.co.main.my.setting.crop

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kr.co.main.R
import kr.co.ui.ext.scaffoldBackground
import kr.co.ui.icon.DreamIcon
import kr.co.ui.icon.dreamicon.Apple
import kr.co.ui.icon.dreamicon.Garlic
import kr.co.ui.icon.dreamicon.Lettuce
import kr.co.ui.icon.dreamicon.Nappacabbage
import kr.co.ui.icon.dreamicon.Pepper
import kr.co.ui.icon.dreamicon.Potato
import kr.co.ui.icon.dreamicon.Rice
import kr.co.ui.icon.dreamicon.Strawberry
import kr.co.ui.icon.dreamicon.Sweetpotato
import kr.co.ui.icon.dreamicon.Tomato
import kr.co.ui.theme.NBDreamTheme
import kr.co.ui.theme.colors
import kr.co.ui.theme.typo
import kr.co.ui.widget.DreamButton
import kr.co.ui.widget.DreamCenterTopAppBar

@Composable
internal fun MyPageCropSelectRoute(
    viewModel: MyPageCropSelectViewModel = hiltViewModel(),
    popBackStack: () -> Unit = {},
    navigateToMyPage: () -> Unit = {},
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.showMyPage.collect {
            navigateToMyPage()
        }
    }

    MyPageCropSelectScreen(
        popBackStack = popBackStack,
        crops = state.crops,
        onCropSelected = viewModel::onCropSelected,
        onConfirmClick = viewModel::onConfirmClick
    )
}

@Composable
private fun MyPageCropSelectScreen(
    popBackStack: () -> Unit = {},
    crops: List<String> = emptyList(),
    onCropSelected: (String) -> Unit = {},
    onConfirmClick: () -> Unit = {},
) {
    Scaffold(
        containerColor = MaterialTheme.colors.white,
        topBar = {
            DreamCenterTopAppBar(
                title = "저장한 글 보기",
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
        },
        bottomBar = {
            DreamButton(
                modifier = Modifier.padding(bottom = 24.dp),
                text = "선택",
                onClick = onConfirmClick
            )
        }
    ) { scaffoldPadding ->
        Column(
            modifier = Modifier
                .scaffoldBackground(scaffoldPadding)
        ) {
            Spacer(modifier = Modifier.height(52.dp))

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "재배 작물을 선택해주세요",
                    style = MaterialTheme.typo.pageName,
                    color = MaterialTheme.colors.gray1
                )

                Text(
                    text = "중복 선택 가능",
                    style = MaterialTheme.typo.body2,
                    color = MaterialTheme.colors.gray5,
                )
            }

            Spacer(modifier = Modifier.height(48.dp))

            Column(
                modifier = Modifier
                    .weight(1f),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Crops.entries.chunked(3).forEach { row ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        row.forEach { crop ->
                            Box(
                                modifier = Modifier
                                    .background(
                                        color = if (crops.contains(crop.value)) Color(0xFF71C16B).copy(0.3f) else Color.Transparent,
                                        shape = CircleShape
                                    )
                                    .clickable { onCropSelected(crop.value) }
                                    .size(84.dp)
                                    .padding(16.dp),
                            ) {

                                Icon(
                                    modifier = Modifier.fillMaxSize(),
                                    imageVector = crop.icon,
                                    contentDescription = crop.value,
                                    tint = Color.Unspecified
                                )
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier)
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    NBDreamTheme {
        MyPageCropSelectScreen()
    }
}

private enum class Crops(
    val value: String,
    val icon: ImageVector,
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
        icon = DreamIcon.Apple,
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