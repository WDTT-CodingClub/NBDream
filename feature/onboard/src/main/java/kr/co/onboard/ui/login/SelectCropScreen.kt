package kr.co.onboard.ui.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kr.co.nbdream.core.ui.R
import kr.co.onboard.login.DescriptionText
import kr.co.onboard.login.DynamicStepProgressBars
import kr.co.onboard.login.Title
import kr.co.ui.theme.ColorSet
import kr.co.ui.theme.NBDreamTheme
import kr.co.ui.theme.typo
import kr.co.ui.widget.NextButton

@Composable
fun SelectCropScreen() {
    val crops = listOf(
        CropItem("고추", R.drawable.img_logo),
        CropItem("벼", R.drawable.img_logo),
        CropItem("감자", R.drawable.img_logo),
        CropItem("고구마", R.drawable.img_logo),
        CropItem("사과", R.drawable.img_logo),
        CropItem("딸기", R.drawable.img_logo),
        CropItem("마늘", R.drawable.img_logo),
        CropItem("상추", R.drawable.img_logo),
        CropItem("배추", R.drawable.img_logo),
        CropItem("토마토", R.drawable.img_logo)
    )
    Column(
        modifier = Modifier
            .padding(16.dp)
    ) {
        Title("내 농장 정보")
        DynamicStepProgressBars(colors = listOf(ColorSet.Dream.lightColors.green2, ColorSet.Dream.lightColors.green2))
        StepText("2/2", modifier = Modifier)
        DescriptionText("재배 작물을 선택해주세요(중복 선택 가능)")
        CropsList(crops)
        NextButton()
    }
}


@Composable
fun StepText(
    text: String,
    modifier: Modifier
){
    Box(
        modifier.fillMaxWidth(),
        contentAlignment = Alignment.CenterEnd
    ){
        Text(
            text,
            color = ColorSet.Dream.lightColors.grey6,
            style = MaterialTheme.typo.labelL) // 피그마에 명시된 폰트가 없어서 임시로 제일 작고 얇은 폰트 적용
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
                modifier = Modifier
                    .padding(12.dp)
                    .fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = Color.Transparent
                )
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(16.dp)
                ) {
                    Image(
                        painter = painterResource(id = cropList[index].image),
                        contentDescription = cropList[index].name,
                        modifier = Modifier.size(64.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = cropList[index].name,
                        fontSize = 16.sp,
                        color = ColorSet.Dream.lightColors.text2,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}

@Composable
fun LazyVerticalGridDemo(
    modifier: Modifier
){
    val list = (1..10).map { it.toString() }

    LazyVerticalGrid(
        columns = GridCells.Fixed(3),

        // content padding
        contentPadding = PaddingValues(
            start = 12.dp,
            top = 16.dp,
            end = 12.dp,
            bottom = 16.dp
        ),
        content = {
            items(list.size) { index ->
                Card(
                    modifier = Modifier
                        .padding(20.dp)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = list[index],
                        fontWeight = FontWeight.Bold,
                        fontSize = 30.sp,
                        color = Color(0xFFFFFFFF),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }
    )
}
data class CropItem(
    val name: String,
    val image: Int
)


@Composable
@Preview(showSystemUi = true)
private fun LazyVerticalGridDemoPreview() {
    val crops = listOf(
        CropItem("고추", R.drawable.img_logo),
        CropItem("벼", R.drawable.img_logo),
        CropItem("감자", R.drawable.img_logo),
        CropItem("고구마", R.drawable.img_logo),
        CropItem("사과", R.drawable.img_logo),
        CropItem("딸기", R.drawable.img_logo),
        CropItem("마늘", R.drawable.img_logo),
        CropItem("상추", R.drawable.img_logo),
        CropItem("배추", R.drawable.img_logo),
        CropItem("토마토", R.drawable.img_logo)
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