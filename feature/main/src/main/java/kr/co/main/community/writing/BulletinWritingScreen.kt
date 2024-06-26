package kr.co.main.community.writing

import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
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
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import kr.co.domain.entity.BulletinEntity
import kr.co.main.R
import kr.co.main.community.SharingData
import kr.co.main.community.temp.UriUtil
import kr.co.main.community.temp.WritingSelectedImageModel
import kr.co.ui.ext.scaffoldBackground
import kr.co.ui.theme.NBDreamTheme
import kr.co.ui.theme.colors
import kr.co.ui.theme.typo
import kr.co.ui.widget.DreamCenterTopAppBar
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
        onCategoryClick = viewModel::onCategoryClick,
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
    onCategoryClick: (BulletinEntity.BulletinCategory) -> Unit = {},
) {

    Scaffold(
        modifier = modifier,
        topBar = {
            DreamCenterTopAppBar(
                title = "글쓰기",
                navigationIcon = {
                    IconButton(onClick = popBackStack) {
                        Icon(
                            modifier = Modifier.size(32.dp),
                            imageVector = Icons.AutoMirrored.Default.KeyboardArrowLeft,
                            contentDescription = stringResource(R.string.feature_main_pop_back_stack)
                        )
                    }
                },
                actions = {
                    TextButton(onClick = {
                        setIsShowWaitingDialog(true)
                        onFinishWritingClick(popBackStack)
                    }) {
                        Text(
                            text = "등록",
                            style = MaterialTheme.typo.body2,
                            color = MaterialTheme.colors.gray3
                        )
                    }
                },
            )
        },
    ) { paddingValues ->

        val multiplePhotoPickerLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.PickMultipleVisualMedia(),
            onResult = { uris ->
                onAddImagesClick(uris) { UriUtil.toPngFile(context, it) }
            },
        )

        LazyColumn(
            modifier = Modifier.scaffoldBackground(paddingValues),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
//            item {
//                Row {
//                    IconButton(onClick = popBackStack) {
//                        Icon(
//                            painter = painterResource(id = kr.co.nbdream.core.ui.R.drawable.baseline_keyboard_arrow_left_24),
//                            contentDescription = "뒤로가기 아이콘",
//                        )
//                    }
//                    Text(
//                        "${state.currentBoard.koreanName} 글쓰기",
//                        modifier = Modifier.weight(1f),
//                    )
//                    TextButton(onClick = {
//                        setIsShowWaitingDialog(true)
//                        onFinishWritingClick(popBackStack)
//                    }) {
////                TextButton(onClick = onFinishWritingClick) {
//                        Text(
//                            "등록",
//                            Modifier.padding(horizontal = 20.dp, vertical = 0.dp),
//                        )
//                    }
//                }
//            }
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    for (category in BulletinEntity.BulletinCategory.entries) {
                        CategoryButton(
                            onClick = { onCategoryClick(category) },
                            text = category.koreanName,
                            isSelected = state.currentCategory == category,
                        )
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
                    placeholder = { Text("'${state.currentBoard.koreanName}'에 대해 이야기해보세요!") },
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
}

@Composable
private fun CategoryButton(
    onClick: () -> Unit,
    text: String,
    isSelected: Boolean,
    modifier: Modifier = Modifier,
) {
    TextButton(
        onClick = onClick,
        modifier = modifier,
        shape = RoundedCornerShape(40),
        colors = if (isSelected) ButtonDefaults.textButtonColors(
            containerColor = MaterialTheme.colors.gray4,
        ) else ButtonDefaults.textButtonColors(
            containerColor = MaterialTheme.colors.gray8,
        ),
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(horizontal = 8.dp),
            color = if (isSelected) MaterialTheme.colors.white else MaterialTheme.colors.gray4,
        )
    }
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
