package kr.co.onboard.crop

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import kr.co.domain.entity.type.CropType
import kr.co.nbdream.core.ui.R
import kr.co.onboard.address.DescriptionText
import kr.co.onboard.address.DynamicStepProgressBars
import kr.co.onboard.crop.model.CropItem
import kr.co.onboard.crop.model.toKoreanName
import kr.co.ui.theme.ColorSet
import kr.co.ui.theme.NBDreamTheme
import kr.co.ui.theme.Paddings
import kr.co.ui.theme.typo
import kr.co.ui.widget.DreamCenterTopAppBar
import kr.co.ui.widget.NextButton

@Composable
fun SelectCropScreen(
    modifier: Modifier = Modifier,
    viewModel: SelectCropViewModel = hiltViewModel()
) {
    val crops by viewModel.crops.collectAsState()

    Scaffold(
        modifier = modifier.padding(Paddings.xlarge),
        topBar = {
            DreamCenterTopAppBar(title = stringResource(id = kr.co.onboard.R.string.feature_onboard_my_farm_title))
        }
    ) { paddingValues ->
        Column(
            modifier = modifier
                .padding(paddingValues)
                .fillMaxHeight()
        ) {
            DynamicStepProgressBars(
                modifier,
                colors = listOf(
                    ColorSet.Dream.lightColors.green2,
                    ColorSet.Dream.lightColors.green2
                )
            )
            StepText(
                stringResource(id = kr.co.onboard.R.string.feature_onboard_step_bar_second),
                modifier = Modifier
            )
            DescriptionText(stringResource(id = kr.co.onboard.R.string.feature_onboard_my_farm_crops_description))
            CropsList(crops)
            NextButton(
                skipId = kr.co.onboard.R.string.feature_onboard_my_farm_skip_select,
                nextId = kr.co.onboard.R.string.feature_onboard_my_farm_next
            )
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
            style = MaterialTheme.typo.labelL
        ) // 피그마에 명시된 폰트가 없어서 임시로 제일 작고 얇은 폰트 적용
    }
}

@Composable
fun CropsList(
    cropList: List<CropItem>,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = modifier.fillMaxWidth(),
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(cropList.size) { index ->
            Card(
                modifier = modifier
                    .padding(Paddings.large)
                    .fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = Color.Transparent
                )
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = modifier.padding(Paddings.xlarge)
                ) {
                    Image(
                        painter = painterResource(id = cropList[index].imageRes),
                        contentDescription = cropList[index].name.name,
                        modifier = modifier.size(64.dp)
                    )
                    Spacer(modifier = modifier.height(8.dp))
                    Text(
                        text = cropList[index].name.toKoreanName(),
                        fontSize = 16.sp,
                        color = ColorSet.Dream.lightColors.text2,
                        textAlign = TextAlign.Center,
                        modifier = modifier.fillMaxWidth()
                    )
                }
            }
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
        CropItem(CropType.TOMATO, R.drawable.img_logo)
    )
    NBDreamTheme {
        CropsList(crops, modifier = Modifier)
    }
}

@Composable
@Preview(showSystemUi = true)
private fun SelectCropScreenPreview() {
    NBDreamTheme {
        SelectCropScreen()
    }
}