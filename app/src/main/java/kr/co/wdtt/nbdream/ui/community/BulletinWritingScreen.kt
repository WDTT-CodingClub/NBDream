package kr.co.wdtt.nbdream.ui.community

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kr.co.wdtt.nbdream.R

@Composable
fun BulletinWritingScreen(
    modifier: Modifier = Modifier,
    viewModel: CommunityViewModel = hiltViewModel(),
) {
    val writing = viewModel.bulletinWritingInput.collectAsState().value

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        item {
            Row {
                Image(
                    painter = painterResource(id = R.drawable.baseline_keyboard_arrow_left_24),
                    contentDescription = "뒤로가기 아이콘",
                )
                Text("감자 글쓰기")
                Image(
                    painter = painterResource(id = R.drawable.baseline_share_24),
                    contentDescription = "공유 아이콘",
                )
            }
        }
        item {
            Card(
                modifier = Modifier
                    .height(40.dp)
                    .border(
                        width = 1.dp,
                        color = Color(0),
                        shape = RoundedCornerShape(12.dp)
                    ),
                colors = CardColors(
                    containerColor = Color.Green,
                    contentColor = Color.Black,
                    disabledContainerColor = Color.LightGray,
                    disabledContentColor = Color.Black,
                )
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround,
                ) {
                    Text("자유 주제")
                    // TODO: 디바이더에도 여백이 붙는데...
                    VerticalDivider(
                        modifier = Modifier
                            .width(1.dp),
                        color = Color.Red,
                    )
                    Text("질문")
                    VerticalDivider(
                        modifier = Modifier
                            .width(1.dp),
                        color = Color.Red,
                    )
                    Text("병해충")
                }
            }
        }
        item {
            TextField(
                value = writing,
                onValueChange = {
                    if (it.length <= 3000) {
                        viewModel.onBulletinWritingInputChanged(it)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp),
                placeholder = { Text("내용을 입력하세요") },
            )
        }
        item {
            Text("${writing.length}/3000")
        }
        item {
            Text("사진")
        }
        item {
            Row {
                Image(
                    painter = painterResource(id = R.drawable.outline_photo_camera_24),
                    contentDescription = "사진 추가 아이콘",
                )
                Text("사진 올리기")
            }
        }
    }
}

@Preview(heightDp = 1200)
@Composable
private fun BulletinWritingScreenPreview() {
    MaterialTheme {
        Surface {
            BulletinWritingScreen()
        }
    }
}
