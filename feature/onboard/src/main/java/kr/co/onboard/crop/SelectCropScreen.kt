package kr.co.onboard.crop

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import kr.co.domain.entity.type.CropType
import kr.co.nbdream.core.ui.R
import kr.co.onboard.address.DynamicStepProgressBars
import kr.co.onboard.crop.model.CropItem
import kr.co.onboard.crop.model.toKoreanName
import kr.co.ui.ext.noRippleClickable
import kr.co.ui.ext.scaffoldBackground
import kr.co.ui.icon.DreamIcon
import kr.co.ui.icon.dreamicon.Apple
import kr.co.ui.icon.dreamicon.Arrowleft
import kr.co.ui.icon.dreamicon.Garlic
import kr.co.ui.icon.dreamicon.Lettuce
import kr.co.ui.icon.dreamicon.Nappacabbage
import kr.co.ui.icon.dreamicon.Pepper
import kr.co.ui.icon.dreamicon.Potato
import kr.co.ui.icon.dreamicon.Rice
import kr.co.ui.icon.dreamicon.Strawberry
import kr.co.ui.icon.dreamicon.Sweetpotato
import kr.co.ui.icon.dreamicon.Tomato
import kr.co.ui.theme.ColorSet
import kr.co.ui.theme.NBDreamTheme
import kr.co.ui.theme.Paddings
import kr.co.ui.theme.colors
import kr.co.ui.theme.typo
import kr.co.ui.widget.DreamCenterTopAppBar
import kr.co.ui.widget.NextButton

@Composable
internal fun SelectCropScreen(
    navController: NavController,
    popBackStack: () -> Unit = {},
    modifier: Modifier = Modifier,
    onCropSelected: (String) -> Unit = {},
    viewModel: SelectCropViewModel = hiltViewModel()
) {

    val crops by viewModel.crops.collectAsState()
    val selectedCropNames = remember { mutableStateListOf<String>() }

    val backStackEntry = navController.currentBackStackEntry
    val fullRoadAddress = backStackEntry?.arguments?.getString("fullRoadAddress")
    val bCode = backStackEntry?.arguments?.getString("bCode")
    val latitude = backStackEntry?.arguments?.getFloat("latitude") ?: 0F
    val longitude = backStackEntry?.arguments?.getFloat("longitude") ?: 0F


    Scaffold(
        containerColor = MaterialTheme.colors.white,
        topBar = {
            DreamCenterTopAppBar(
                title = stringResource(id = kr.co.onboard.R.string.feature_onboard_my_farm_title),
                colorBackground = true,
                navigationIcon = {
                    IconButton(
                        onClick = popBackStack
                    ) {
                        Icon(
                            modifier = Modifier.size(32.dp),
                            imageVector = DreamIcon.Arrowleft,
                            contentDescription = "arrowleft"
                        )
                    }
                }
            )
        }
    ) { scaffoldPadding ->

        Column(
            modifier = Modifier
                .scaffoldBackground(scaffoldPadding)
        ) {
            DynamicStepProgressBars(
                modifier,
                colors = listOf(
                    ColorSet.Dream.lightColors.green4,
                    ColorSet.Dream.lightColors.green4
                )
            )
            Spacer(modifier = Modifier.height(52.dp))

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = stringResource(id = kr.co.onboard.R.string.feature_onboard_my_farm_crops_first_description),
                    style = MaterialTheme.typo.pageName,
                    color = MaterialTheme.colors.gray1
                )

                Text(
                    text = stringResource(id = kr.co.onboard.R.string.feature_onboard_my_farm_crops_second_description),
                    style = MaterialTheme.typo.body2,
                    color = MaterialTheme.colors.gray5,
                )
            }

            Spacer(modifier = Modifier.height(52.dp))

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
                            val isSelected = selectedCropNames.contains(crop.value)
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center,
                                modifier = Modifier
                                    .noRippleClickable {
                                        if (isSelected)
                                            selectedCropNames.remove(crop.value)
                                        else
                                            selectedCropNames.add(crop.value)
                                        onCropSelected(crop.value)
                                    }
                            ) {
                                Box(
                                    modifier = Modifier
                                        .background(
                                            color = if (isSelected) Color(0xFF71C16B).copy(alpha = 0.3f) else Color.Transparent,
                                            shape = CircleShape
                                        )
                                        .size(88.dp)
                                        .padding(8.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        modifier = Modifier.size(56.dp),
                                        imageVector = crop.icon,
                                        contentDescription = crop.value,
                                        tint = Color.Unspecified
                                    )
                                }
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = crop.value,
                                    textAlign = TextAlign.Center,
                                    style = MaterialTheme.typo.body2,
                                    modifier = Modifier.align(Alignment.CenterHorizontally)
                                )
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier)

                NextButton(
                    skipId = kr.co.onboard.R.string.feature_onboard_my_farm_skip_select,
                    nextId = kr.co.onboard.R.string.feature_onboard_my_farm_next,
                    onNextClick = {
                        val cropsString = if(selectedCropNames.isEmpty()) null else selectedCropNames.joinToString(",")
                        navController.navigate(
                            "WelcomeScreen/$fullRoadAddress/$bCode/$latitude/$longitude/$cropsString"
                        ) },
                    onSkipClick = {
                        val cropsString = null
                        navController.navigate(
                            "WelcomeScreen/$fullRoadAddress/$bCode/$latitude/$longitude/$cropsString"
                        ) }
                )
            }
        }
    }
}

@Composable
fun StepText(
    text: String,
    modifier: Modifier
) {
    Box(
        modifier.fillMaxWidth(),
        contentAlignment = Alignment.CenterEnd
    ) {
        Text(
            text,
            color = ColorSet.Dream.lightColors.grey6,
            style = MaterialTheme.typo.labelL // 피그마에 명시된 폰트가 없어서 임시로 제일 작고 얇은 폰트 적용
        )
    }
}

@Composable
fun CropsList(
    cropList: List<CropItem>,
    onItemClick: (Int) -> Unit,
    selectedCropNames: List<String> = emptyList(),
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(cropList.size) { index ->
            val isSelected = selectedCropNames.contains(cropList[index].name.toKoreanName())
            val backgroundColor =
                if (isSelected) ColorSet.Dream.lightColors.green4.copy(alpha = 0.3f) else Color.Transparent
            Card(
                shape = CircleShape,
                modifier = Modifier
                    .padding(Paddings.medium)
                    .fillMaxWidth(1f / 3f)
                    .aspectRatio(1f)
                    .clickable {
                        onItemClick(index)
                    },
                colors = CardDefaults.cardColors(
                    containerColor = backgroundColor
                )
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    Image(
                        painter = painterResource(id = cropList[index].imageRes),
                        contentDescription = cropList[index].name.name,
                        modifier = Modifier.size(64.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = cropList[index].name.toKoreanName(),
                fontSize = 16.sp,
                color = ColorSet.Dream.lightColors.text2,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(horizontal = 4.dp)
            )
        }
    }
}

@Composable
@Preview(showSystemUi = true)
private fun LazyVerticalGridDemoPreview() {
    val crops = listOf(
        CropItem(CropType.PEPPER, R.drawable.img_logo),
        CropItem(CropType.RICE, R.drawable.img_logo),
        CropItem(CropType.POTATO, R.drawable.img_logo),
        CropItem(CropType.SWEET_POTATO, R.drawable.img_logo),
        CropItem(CropType.APPLE, R.drawable.img_logo),
        CropItem(CropType.STRAWBERRY, R.drawable.img_logo),
        CropItem(CropType.GARLIC, R.drawable.img_logo),
        CropItem(CropType.LETTUCE, R.drawable.img_logo),
        CropItem(CropType.NAPPA_CABBAGE, R.drawable.img_logo),
        CropItem(CropType.TOMATO, R.drawable.img_logo),
        CropItem(CropType.LETTUCE, R.drawable.img_logo),
        CropItem(CropType.NAPPA_CABBAGE, R.drawable.img_logo),
        CropItem(CropType.TOMATO, R.drawable.img_logo)
    )
    NBDreamTheme {
        CropsList(crops, {}, modifier = Modifier)
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

}