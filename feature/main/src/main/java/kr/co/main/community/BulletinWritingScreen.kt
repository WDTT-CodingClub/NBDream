package kr.co.main.community

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage

@Composable
fun BulletinWritingScreen(
    modifier: Modifier = Modifier,
    viewModel: CommunityViewModel = hiltViewModel(),
) {
    val writing = viewModel.bulletinWritingInput.collectAsState().value
    val writingImages = viewModel.writingImages.collectAsState().value

    val multiplePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickMultipleVisualMedia(),
        onResult = { uris ->
            viewModel.addImages(uris)
        }
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        item {
            Row {
                IconButton(onClick = { /*TODO*/ }) {
                    Image(
                        painter = painterResource(id = kr.co.nbdream.core.ui.R.drawable.baseline_keyboard_arrow_left_24),
                        contentDescription = "뒤로가기 아이콘",
                    )
                }
                Text(
                    "감자 글쓰기",
                    modifier = Modifier.weight(1f),
                )
                TextButton(onClick = {
                }) {
                    Text(
                        "등록",
                        Modifier.padding(horizontal = 20.dp, vertical = 0.dp),
                    )
                }
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
            LazyRow(
                modifier = Modifier.height(120.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                item {
                    Box(
                        modifier = Modifier
                            .size(120.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color.LightGray)
                            .clickable {
                                multiplePhotoPickerLauncher.launch(
                                    PickVisualMediaRequest(
                                        ActivityResultContracts.PickVisualMedia.ImageOnly
                                    )
                                )
                            },
                        contentAlignment = Alignment.Center,
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Image(
                                painter = painterResource(id = kr.co.nbdream.core.ui.R.drawable.outline_photo_camera_24),
                                contentDescription = "카메라 아이콘",
                            )
                            Text("사진 추가")
                        }
                    }
                }
                items(writingImages) {
                    Box(
                        modifier = Modifier
                            .size(120.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color.LightGray),
                        contentAlignment = Alignment.Center,
                    ) {
                        AsyncImage(
                            model = it.uri,
                            contentDescription = "image",
                            contentScale = ContentScale.Crop,
                        )
                        IconButton(
                            onClick = {
                                it.uri?.let { viewModel.removeImage(it) }
                            },
                            modifier = Modifier.align(Alignment.TopEnd),
                            colors = IconButtonDefaults.iconButtonColors(
                                containerColor = Color(
                                    0x99999999
                                )
                            ),
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Clear,
                                contentDescription = "이미지 삭제 아이콘",
                            )
                        }
                    }
                }
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
