package kr.co.main.community.writing

import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import kr.co.main.community.SharingData
import kr.co.main.community.temp.UriUtil
import kr.co.main.community.temp.WritingSelectedImageModel
import kr.co.ui.theme.NBDreamTheme
import java.io.File

@Composable
internal fun BulletinWritingRoute(
    popBackStack: () -> Unit,
    sharingData: SharingData,
    modifier: Modifier = Modifier,
    viewModel: BulletinWritingViewModel = hiltViewModel(),
) {
    LaunchedEffect(key1 = sharingData) {
        viewModel.setSharingData(sharingData)
    }
    val context = LocalContext.current
    val state by viewModel.state.collectAsStateWithLifecycle()
    BulletinWritingScreen(
        modifier = modifier,
        state = state,
        context = context,
        popBackStack = popBackStack,
        onAddImagesClick = viewModel::onAddImagesClick,
        onBulletinWritingInputChanged = viewModel::onBulletinWritingInputChanged,
        onRemoveImageClick = viewModel::onRemoveImageClick,
        onFinishWritingClick = viewModel::onFinishWritingClick,
        setIsShowWaitingDialog = viewModel::setIsShowWaitingDialog,
    )
}

@Composable
internal fun BulletinWritingScreen(
    modifier: Modifier = Modifier,
    state: BulletinWritingViewModel.State = BulletinWritingViewModel.State(),
    context: Context = LocalContext.current,
    popBackStack: () -> Unit = {},
    onAddImagesClick: (uris: List<Uri>, (Uri) -> File) -> Unit = { _, _ -> },
    onBulletinWritingInputChanged: (input: String) -> Unit = {},
    onRemoveImageClick: (model: WritingSelectedImageModel) -> Unit = {},
    onFinishWritingClick: (() -> Unit) -> Unit = {},
    setIsShowWaitingDialog: (Boolean) -> Unit = {},
) {

    val multiplePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickMultipleVisualMedia(),
        onResult = { uris ->
            onAddImagesClick(uris) { UriUtil.toPngFile(context, it) }
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
                IconButton(onClick = popBackStack) {
                    Icon(
                        painter = painterResource(id = kr.co.nbdream.core.ui.R.drawable.baseline_keyboard_arrow_left_24),
                        contentDescription = "뒤로가기 아이콘",
                    )
                }
                Text(
                    "${state.currentBoard.koreanName} 글쓰기",
                    modifier = Modifier.weight(1f),
                )
                TextButton(onClick = {
                    setIsShowWaitingDialog(true)
                    onFinishWritingClick(popBackStack)
                }) {
//                TextButton(onClick = onFinishWritingClick) {
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
                        width = 1.dp, color = Color(0), shape = RoundedCornerShape(12.dp)
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
                        thickness = 1.dp,
                        color = Color.Red,
                    )
                    Text("질문")
                    VerticalDivider(
                        thickness = 1.dp,
                        color = Color.Red,
                    )
                    Text("병해충")
                }
            }
        }
        item {
            TextField(
                value = state.bulletinWritingInput,
                onValueChange = {
                    if (it.length <= 3000) {
                        onBulletinWritingInputChanged(it)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp),
                placeholder = { Text("내용을 입력하세요") },
            )
        }
        item {
            Text("${state.bulletinWritingInput.length}/3000")
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
                            Icon(
                                painter = painterResource(id = kr.co.nbdream.core.ui.R.drawable.outline_photo_camera_24),
                                contentDescription = "카메라 아이콘",
                            )
                            Text("사진 추가")
                        }
                    }
                }
                items(state.writingImages) {
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
                            onClick = { onRemoveImageClick(it) },
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

    if (state.isShowWaitingDialog) AlertDialogExample(
        onDismissRequest = { setIsShowWaitingDialog(false) },
        onConfirmation = {},
        dialogTitle = "title",
        dialogText = "text",
        icon = null,
    )

}

@Composable
fun AlertDialogExample(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    dialogTitle: String,
    dialogText: String,
    icon: ImageVector?,
) {
    AlertDialog(onDismissRequest = onDismissRequest, confirmButton = { onConfirmation() })

//    AlertDialog(
//        icon = { Icon(icon, contentDescription = "Example Icon") },
//        title = { Text(dialogTitle) },
//        text = { Text(dialogText) },
//        onDismissRequest = onDismissRequest,
//        confirmButton = {
//            TextButton(onClick = onConfirmation) {
//                Text("Confirm")
//            }
//        },
//        dismissButton = {
//            TextButton(onClick = onDismissRequest) {
//                Text("Dismiss")
//            }
//        },
//    )
}

@Preview(heightDp = 1200)
@Composable
private fun BulletinWritingScreenPreview() {
    NBDreamTheme {
        BulletinWritingScreen()
    }
}
